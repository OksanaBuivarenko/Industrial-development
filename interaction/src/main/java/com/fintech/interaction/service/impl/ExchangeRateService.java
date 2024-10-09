package com.fintech.interaction.service.impl;


import com.fintech.interaction.model.ValCurs;
import com.fintech.interaction.model.Valute;
import com.fintech.interaction.service.HttpService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateService implements HttpService<Valute> {

    private final WebClient webClient;

    @Value("${cbr.url}")
    private String url;

    @RateLimiter(name = "basic")
    @CircuitBreaker(name = "basic")
    @Override
    public List<Valute> getListByApi() {
        return webClient
                .get()
                .uri(url)
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToMono(ValCurs.class)
                .block().getValute();
    }
}