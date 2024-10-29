package com.fintech.multithreading.service.impl;

import com.fintech.multithreading.model.Dates;
import com.fintech.multithreading.model.Events;
import com.fintech.multithreading.service.EventHttpService;
import com.fintech.multithreading.service.RateLimiterService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.Semaphore;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RateLimiterServiceImplTest {

    private final Semaphore Semaphore = new Semaphore( 10);

    EventHttpService eventHttpService = Mockito.mock(EventHttpService.class);

    RateLimiterService<Mono<List<Events>>> rateLimiterService = new RateLimiterServiceImpl(Semaphore, eventHttpService);

    @Test
    void limitedResultSuccess() {
        Mono<List<Events>> events = Mono.just(List.of(new Events(1L,"Events", List.of(new Dates()), "100.0")));
        when(eventHttpService.getListByApiMono("", "")).thenReturn(events);

        Mono<List<Events>> result = rateLimiterService.limited("","");

        assertEquals(events, result);
    }

    @Test
    void limitedSuccess() {
        Mono<List<Events>> events = Mono.just(List.of(new Events(1L,"Events", List.of(new Dates()), "100.0")));
        when(eventHttpService.getListByApiMono("", "")).thenReturn(events);

        for (int i = 0; i < 5; i++) {
            rateLimiterService.limited("","");
        }

        verify(eventHttpService, times(5)).getListByApiMono("","");
    }

    @Test
    void limitedFail() {
        Mono<List<Events>> events = Mono.just(List.of(new Events(1L,"Events", List.of(new Dates()), "100.0")));
        when(eventHttpService.getListByApiMono("", "")).thenReturn(events);

        try {
            for (int i = 0; i < 11; i++) {
                rateLimiterService.limited("","");
            }
        } catch (Exception e) {
            assertEquals("Semaphor not acqured", e.getMessage());
        }
    }
}