package com.fintech.parser.hw5.service.impl;

import com.fintech.parser.hw5.service.FillStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@Service
@RequiredArgsConstructor
public class FillAllStorages {

    private final List<FillStorageService>  fillStorageServiceList;

    private final ExecutorService fixedExecutor;

    private final ScheduledExecutorService scheduledExecutor;

    @Value("${executor.durations}")
    private Duration duration;

    @EventListener(ContextRefreshedEvent.class)
    public void fillStoragesOnStart() {
        try {
            List<CompletableFuture<Void>> futureList = new ArrayList<>();
            for (FillStorageService service : fillStorageServiceList) {
                CompletableFuture<Void> cf = supplyAsync(() -> {
                        service.fillStorage();
                        log.info("Method fill work  from thread " + getCurrentThreadName());
                    return null;
                }, fixedExecutor);
                futureList.add(cf);
            }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()])).join();
        } catch (Exception e) {
            log.error("Fill storages failed. " + e.getMessage());
            throw new RuntimeException("Fill storages failed");
        }
    }

    @EventListener(ContextStartedEvent.class)
    public void schedule() {
        for (FillStorageService service : fillStorageServiceList) {
            scheduledExecutor.scheduleAtFixedRate(() -> service.fillStorage(), duration.toMillis(), duration.toMillis(), TimeUnit.MINUTES);
            log.info("Method schedule work from thread " + getCurrentThreadName());
        }
    }

    public static String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }
}