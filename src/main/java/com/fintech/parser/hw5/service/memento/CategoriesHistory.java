package com.fintech.parser.hw5.service.memento;

import com.fintech.parser.hw5.model.Categories;

public interface CategoriesHistory {

    void save(Categories categories);

    void undo(Categories categories);

    void delete(Long id);
}
