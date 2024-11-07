package com.fintech.parser.hw5.service.observer;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FillStoragesWithObserver {

    private final ExecuteLocations executeLocations;

    private final FillLocations fillLocations;

    private final ExecuteCategory executeCategory;

    private final FillCategory fillCategory;

    public void fillLocationStorage() {
        executeLocations.addObserver(fillLocations);
        executeLocations.getListByApi();
    }

    public void fillCategoryStorage() {
        executeCategory.addObserver(fillCategory);
        executeCategory.getListByApi();
    }
}