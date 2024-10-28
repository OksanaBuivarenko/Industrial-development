package com.fintech.parser.hw5.model;

import com.fintech.parser.hw5.service.memento.CategoriesMemento;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Categories {

    private Long id;

    private String slug;

    private String name;

    public CategoriesMemento save() {
        return new CategoriesMemento(id, slug, name);
    }

    public Categories restore(CategoriesMemento memento) {
        setName(memento.getName());
        setSlug(memento.getSlug());
        return this;
    }
}