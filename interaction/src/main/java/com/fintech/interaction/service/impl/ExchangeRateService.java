package com.fintech.interaction.service.impl;

import com.fintech.interaction.model.ValCurs;
import com.fintech.interaction.model.Valute;
import com.fintech.interaction.service.HttpService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateService implements HttpService<String, Double> {

    private final WebClient webClient;

    @Value("${cbr.url}")
    private String url;

    @Cacheable(cacheNames = "cache", cacheManager = "cacheManager", sync = true)
    @RateLimiter(name = "basic")
    @CircuitBreaker(name = "basic")
    @Override
    public Map<String, Double> getMapByApi() {
        log.info("Call method getMapByApi");
        List<Valute> valuteList = webClient
                .get()
                .uri(url)
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToMono(ValCurs.class)
                .block().getValute();
        return getValuteMap(valuteList);
    }

    private Map<String, Double> getValuteMap(List<Valute> valuteList) {
       return valuteList.stream().collect(Collectors.toMap(Valute::getCharCode, Valute::getVunitRate));
    }
}