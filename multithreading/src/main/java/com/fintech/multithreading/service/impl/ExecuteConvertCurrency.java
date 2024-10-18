package com.fintech.multithreading.service.impl;

import com.fintech.multithreading.dto.ConvertCurrencyDto;
import com.fintech.multithreading.dto.CurrencyDto;
import com.fintech.multithreading.service.CurrencyHttpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecuteConvertCurrency implements CurrencyHttpService {

    @Override
    public Double getAmount(CurrencyDto currencyDto) {
        RestClient restClient = RestClient.create();
        return restClient
                .post()
                .uri("http://localhost:8888/currencies/convert")
                .body(currencyDto)
                .retrieve()
                .toEntity(ConvertCurrencyDto.class)
                .getBody().getConvertedAmount();
    }

    public Mono<Double> getAmountMono(CurrencyDto currencyDto) {
        Mono<CurrencyDto> currencyMono = Mono.just(currencyDto);
        WebClient webClient = WebClient.builder().build();
        return webClient
                .post()
                .uri("http://localhost:8888/currencies/convert")
                .body(currencyMono, CurrencyDto.class)
                .retrieve()
                .bodyToMono(ConvertCurrencyDto.class)
                .map(ConvertCurrencyDto::getConvertedAmount);
    }
}