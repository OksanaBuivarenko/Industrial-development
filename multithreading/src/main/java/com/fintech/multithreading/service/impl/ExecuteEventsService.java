package com.fintech.multithreading.service.impl;

import com.fintech.multithreading.model.EventList;
import com.fintech.multithreading.model.Events;
import com.fintech.multithreading.service.EventHttpService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecuteEventsService implements EventHttpService {

    private static Integer permits = 1;
    private static Boolean fair = false;

    @Value("${rate_limiter.permits}")
    private Integer injectionPermits;

    @Value("${rate_limiter.fair}")
    private Boolean injectionFair;

    @PostConstruct
    public void init() {
        permits = injectionPermits;
        fair = injectionFair;
    }

    private static final Semaphore SEMAPHORE = new Semaphore(permits, fair);

    private final WebClient webClient;

    @Value("${url.events}")
    private String url;

    @SneakyThrows
    @Override
    public List<Events> getListByApi(String actualSince, String actualUntil) {
        List<Events> resultList = new ArrayList<>();

        var ref = new Object() {
            int pageNumber = 1;
        };

        var isLastPage = true;

        while (isLastPage) {
            EventList list = webClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path(url)
                            .queryParam("page", ref.pageNumber)
                            .queryParam("page_size", 100)
                            .queryParam("fields", "id,title,dates,price")
                            .queryParam("order_by", "favorites_count")
                            .queryParam("actual_since", actualSince)
                            .queryParam("actual_until", actualUntil)
                            .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(EventList.class)
                    .toFuture()
                    .get();

            resultList.addAll(list.getResults());
            if (list.getNext() == null) {
                isLastPage = false;
            }
            ref.pageNumber++;
        }
        return resultList;
    }


    public Mono<List<Events>> getListByApiMono(String actualSince, String actualUntil) {
        WebClient client = WebClient.builder()
                .baseUrl("https://kudago.com/public-api/")
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().followRedirect(true)))
                .build();

        try {
            SEMAPHORE.acquire();
            return client
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path(url)
                            .queryParam("page_size", 100)
                            .queryParam("fields", "id,title,dates,price")
                            .queryParam("order_by", "favorites_count")
                            .queryParam("actual_since", actualSince)
                            .queryParam("actual_until", actualUntil)
                            .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(EventList.class)
                    .map(EventList::getResults);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            SEMAPHORE.release();
        }
    }
}