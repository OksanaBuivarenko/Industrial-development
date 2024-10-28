package com.fintech.parser.hw5.service.observer;

import com.fintech.parser.hw5.model.Categories;
import com.fintech.parser.hw5.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FillCategory implements Observer<Categories> {

    private final CategoriesRepository categoriesRepository;

    @Override
    public void handleEvent(List<Categories> list) {
        log.info("Start saving list categories to storage");
        list.forEach(categories -> categoriesRepository.save(categories.getId(), categories));
        list.sort(Comparator.comparing(Categories::getId, Comparator.reverseOrder()));
        categoriesRepository.setMaxId(list.get(0).getId());
        log.info("Finish saving list categories to storage");
    }
}
