package com.fintech.parser.hw5.service;


import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

public interface FillStorageService {

    @EventListener(ContextRefreshedEvent.class)
    void fillStorage();
}
