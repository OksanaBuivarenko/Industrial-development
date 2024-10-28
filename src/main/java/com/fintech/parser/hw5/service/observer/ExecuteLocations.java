package com.fintech.parser.hw5.service.observer;

import com.fintech.parser.hw5.model.Locations;
import com.fintech.parser.hw5.service.HttpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ExecuteLocations implements HttpService<Locations>, Observable<Locations> {

    private List<Observer<Locations>> observerList = new ArrayList<>();

    private final WebClient webClient;

    @Value("${url.locations}")
    private String url;

    @Override
    public List<Locations> getListByApi() {
        List<Locations> locationsList = webClient
                .get()
                .uri(url)//"https://kudago.com/public-api/v1.4/locations"
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Locations>>() {})
                .block();
        notifyObserver(locationsList);
        return locationsList;
    }

    @Override
    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObserver(List<Locations> locationsList) {
        observerList.forEach(observer -> observer.handleEvent(locationsList));
    }
}
