package com.fintech.parser.hw5.repository;

import com.fintech.parser.hw5.model.Categories;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Slf4j
@Lazy
@Repository
public class CategoriesRepository extends AppRepository<Long, Categories> {
    public CategoriesRepository() {
        log.info("Init CategoriesRepository");
    }

    private Long maxId;

    public Long getMaxId() {
        return maxId;
    }

    public void setMaxId(Long maxId) {
        this.maxId = maxId;
    }

    public void incrementMaxId() {
        maxId++;
    }
}