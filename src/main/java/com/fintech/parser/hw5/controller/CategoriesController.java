package com.fintech.parser.hw5.controller;

import com.fintech.parser.hw5.dto.request.CategoriesRq;
import com.fintech.parser.hw5.dto.responce.CategoriesRs;
import com.fintech.parser.hw5.service.CategoriesService;
import com.fintech.parser.hw5.dto.responce.DeleteRs;
import com.fintech.timedstarter.annotation.TimedAnnotation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@TimedAnnotation
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/places/categories")

public class CategoriesController {

    private final CategoriesService categoriesService;

    @GetMapping()
    public List<CategoriesRs> getCategories() {
        return categoriesService.getCategoriesRs();
    }

    @GetMapping("/{id}")
    public CategoriesRs getCategoryById(@PathVariable Long id) {
        return categoriesService.getCategoryRsById(id);
    }

    @PostMapping()
    public CategoriesRs createCategory(@RequestBody CategoriesRq categoriesRq) {
        return categoriesService.createCategory(categoriesRq);
    }

    @PutMapping("/{id}")
    public CategoriesRs updateCategory(@PathVariable Long id, @RequestBody CategoriesRq categoriesRq) {
        return categoriesService.updateCategory(id, categoriesRq);
    }

    @DeleteMapping("/{id}")
    public DeleteRs deleteCategory(@PathVariable Long id) {
        return categoriesService.deleteCategory(id);
    }
}