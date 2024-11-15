package com.fintech.parser.hw5.service.impl;

import com.fintech.parser.hw5.service.FillStorageService;
import com.fintech.timedstarter.annotation.TimedAnnotation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class FillAllStorages {

    private final List<FillStorageService>  fillStorageServiceList;

    private final ExecutorService fixedExecutor;

    private final ScheduledExecutorService scheduledExecutor;

    @Value("${executor.period}")
    private Integer period;

    @TimedAnnotation
    @EventListener(ContextRefreshedEvent.class)
    public void fillStoragesOnStart() {
        CountDownLatch latch = new CountDownLatch(2);
        try {
            for (FillStorageService service : fillStorageServiceList) {
                fixedExecutor.submit(() -> {
                    try {
                        service.fillStorage();
                        log.info("Method fill work  from thread " + getCurrentThreadName());
                    } finally {
                        latch.countDown();
                    }
                });
            }
            latch.await();
            } catch (Exception e) {
                log.error("Fill storages failed. " + e.getMessage());
                throw new RuntimeException("Fill storages failed");
            }
        fixedExecutor.shutdown();
    }

    @EventListener(ContextStartedEvent.class)
    public void schedule() {
        for (FillStorageService service : fillStorageServiceList) {
            scheduledExecutor.scheduleAtFixedRate(() -> service.fillStorage(),period, period, TimeUnit.MINUTES);
            log.info("Method schedule work from thread " + getCurrentThreadName());
        }
    }

    public static String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }
}