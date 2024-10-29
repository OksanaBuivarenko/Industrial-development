package com.fintech.multithreading.service;

public interface RateLimiterService<Mono> {
    Mono limited(String actualSince, String actualUntil);
}
