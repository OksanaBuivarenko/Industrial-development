package com.fintech.parser.hw5.service.impl;

import com.fintech.parser.hw5.model.Locations;
import com.fintech.parser.hw5.service.FillStorageService;
import com.fintech.parser.hw5.service.HttpService;
import com.fintech.parser.hw5.service.LocationsService;
import com.fintech.timedstarter.annotation.TimedAnnotation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Lazy
@Service
@Slf4j
public class FillLocationsStorageServiceImpl implements FillStorageService, HttpService<Locations> {

    private final LocationsService locationsService;

    private final WebClient webClient;

    @Value("${url.locations}")
    private String url;

    @Autowired
    @Lazy
    public FillLocationsStorageServiceImpl(LocationsService locationsService, WebClient webClient) {
        log.info("Init FillLocationsStorageServiceImpl");
        this.locationsService = locationsService;
        this.webClient = webClient;
    }

    //@EventListener(ContextRefreshedEvent.class)
    @TimedAnnotation
    @Override
    public void fillStorage() {
        List<Locations> locationsList = getListByApi();
        saveToDataSource(locationsList);
        log.info("Locations storage filed");
    }

    @SneakyThrows
    @Override
    public List<Locations> getListByApi() {
       return webClient
                .get()
                .uri(url)//"https://kudago.com/public-api/v1.4/locations"
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Locations>>() {})
                .block();
    }

    private void saveToDataSource(List<Locations> list) {
        log.info("Start saving list locations to storage");
        list.forEach(locationsService::save);
        log.info("Finish saving list locations to storage");
    }
}