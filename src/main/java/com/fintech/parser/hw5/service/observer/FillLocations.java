package com.fintech.parser.hw5.service.observer;

import com.fintech.parser.hw5.model.Locations;
import com.fintech.parser.hw5.service.LocationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FillLocations implements Observer<Locations> {

    private final LocationsService locationsService;

    @Override
    public void handleEvent(List<Locations> list) {
        log.info("Start saving list locations to storage");
        list.forEach(locationsService::save);
        log.info("Finish saving list locations to storage");

    }
}
