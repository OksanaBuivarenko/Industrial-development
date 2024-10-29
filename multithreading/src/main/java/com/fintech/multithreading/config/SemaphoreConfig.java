package com.fintech.multithreading.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Semaphore;

@Configuration
public class SemaphoreConfig {

    @Bean
    public Semaphore semaphore(ApplicationConfig applicationConfig) {
        return new Semaphore(applicationConfig.getPermits(), applicationConfig.getFair());
    }
}