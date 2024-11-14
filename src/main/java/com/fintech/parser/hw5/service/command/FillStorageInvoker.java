package com.fintech.parser.hw5.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FillStorageInvoker {

    private final Command fillCategoriesStorageCommand;

    private final Command fillLocationsStorageCommand;

    @EventListener(ContextRefreshedEvent.class)
    public void invoke() {
       fillCategoriesStorageCommand.execute();
       fillLocationsStorageCommand.execute();
    }
}