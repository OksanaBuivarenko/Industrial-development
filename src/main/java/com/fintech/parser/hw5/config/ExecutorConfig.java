package com.fintech.parser.hw5.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

@Configuration
public class ExecutorConfig {

    @Value("${executor.thread_count}")
    private Integer threadCount;

    private final ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("fill_storage_thread-%d")
            .setDaemon(true)
            .build();

    @Bean("fixedExecutor")
    public ExecutorService FixedExecutorService() {
        return  Executors.newFixedThreadPool(threadCount, threadFactory);
    }

    @Bean("scheduledExecutor")
    public ScheduledExecutorService ScheduledExecutorService() {
        return  Executors.newScheduledThreadPool(threadCount, threadFactory);
    }
}
