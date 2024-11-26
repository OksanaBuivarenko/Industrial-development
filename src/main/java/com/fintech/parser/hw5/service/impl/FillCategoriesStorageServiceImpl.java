package com.fintech.parser.hw5.service.impl;

import com.fintech.parser.hw5.model.Categories;
import com.fintech.parser.hw5.repository.CategoriesRepository;
import com.fintech.parser.hw5.service.FillStorageService;
import com.fintech.parser.hw5.service.HttpService;
import com.fintech.timedstarter.annotation.TimedAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;
import java.util.Comparator;
import java.util.List;

@Lazy
@Service
@Slf4j
public class FillCategoriesStorageServiceImpl implements FillStorageService, HttpService<Categories> {

    private final CategoriesRepository categoriesRepository;

    private final WebClient webClient;

    @Value("${url.categories}")
    private String url;

    @Autowired
    @Lazy
    public FillCategoriesStorageServiceImpl(CategoriesRepository categoriesRepository, WebClient webClient) {
        log.info("Init FillCategoriesStorageServiceImpl");
        this.categoriesRepository = categoriesRepository;
        this.webClient = webClient;
    }

    //@EventListener(ContextRefreshedEvent.class)
    @TimedAnnotation
    @Override
    public void fillStorage() {
        List<Categories> categoriesList = getListByApi();
        saveToDataSource(categoriesList);
        log.info("Categories storage filed");
    }

    private void saveToDataSource(List<Categories> list) {
        log.info("Start saving list categories to storage");
        list.forEach(categories -> categoriesRepository.save(categories.getId(), categories));
        list.sort(Comparator.comparing(Categories::getId, Comparator.reverseOrder()));
        categoriesRepository.setMaxId(list.get(0).getId());
        log.info("Finish saving list categories to storage");
    }

    @Override
    public List<Categories> getListByApi() {
        return webClient
                .get()
                .uri(url)//"https://kudago.com/public-api/v1.4/place-categories"
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Categories>>() {})
                .block();
    }
}