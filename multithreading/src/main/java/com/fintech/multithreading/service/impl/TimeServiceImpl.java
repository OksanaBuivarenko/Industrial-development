package com.fintech.multithreading.service.impl;

import com.fintech.multithreading.service.TimeService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TimeServiceImpl implements TimeService {

    @Override
    public String getNow() {
        return String.valueOf(Instant.now().getEpochSecond());
    }

    @Override
    public String getPlusWeek() {
        return String.valueOf(Instant.now().plus(7, ChronoUnit.DAYS).getEpochSecond());
    }
}
