package com.fintech.multithreading.service.impl;

import com.fintech.multithreading.model.Events;
import com.fintech.multithreading.service.EventHttpService;
import com.fintech.multithreading.service.RateLimiterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RateLimiterServiceImpl implements RateLimiterService<Mono<List<Events>>> {

    private final Semaphore semaphore;
    private final EventHttpService eventHttpService;

    @Override
    public Mono<List<Events>> limited(String actualSince, String actualUntil) {
        boolean permit = false;
        try {
            semaphore.acquire();
            permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
            if (permit) {
                return eventHttpService.getListByApiMono(actualSince, actualUntil);
            } else {
                throw new RuntimeException("Semaphor not acqured");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            if (permit) {
                semaphore.release();
            }
        }
    }
}