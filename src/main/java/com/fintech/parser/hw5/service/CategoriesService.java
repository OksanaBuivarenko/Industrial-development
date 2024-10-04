package com.fintech.parser.hw5.service;

import com.fintech.parser.hw5.dto.request.CategoriesRq;
import com.fintech.parser.hw5.dto.responce.CategoriesRs;
import com.fintech.parser.hw5.dto.responce.DeleteRs;
import com.fintech.parser.hw5.model.Categories;

import java.util.List;

public interface CategoriesService {

    List<Categories> getCategories();

    List<CategoriesRs> getCategoriesRs();

    Categories getCategoryById(Long id);

    CategoriesRs getCategoryRsById(Long id);

    CategoriesRs createCategory(CategoriesRq categoriesRq);

    CategoriesRs updateCategory(Long id, CategoriesRq categoriesRq);

    DeleteRs deleteCategory(Long id);
}
