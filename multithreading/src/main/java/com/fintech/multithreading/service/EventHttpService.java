package com.fintech.multithreading.service;

import com.fintech.multithreading.model.Events;
import reactor.core.publisher.Mono;

import java.util.List;

public interface EventHttpService {

    List<Events> getListByApi(String actualSince, String actualUntil);

    Mono<List<Events>> getListByApiMono(String actualSince, String actualUntil);
}