package com.fintech.parser.hw5.service.observer;

import com.fintech.parser.hw5.model.Categories;
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
public class ExecuteCategory implements HttpService<Categories>, Observable<Categories> {

    private List<Observer<Categories>> observerList = new ArrayList<>();

    private final WebClient webClient;

    @Value("${url.categories}")
    private String url;

    @Override
    public List<Categories> getListByApi() {
        List<Categories> categoriesList = webClient
                .get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Categories>>() {})
                .block();
        notifyObserver(categoriesList);
        return categoriesList;
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
    public void notifyObserver(List<Categories> categoriesList) {
        observerList.forEach(observer -> observer.handleEvent(categoriesList));
    }
}
