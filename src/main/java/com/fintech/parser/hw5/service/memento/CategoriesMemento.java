package com.fintech.parser.hw5.service.memento;

import lombok.Getter;

@Getter
public class CategoriesMemento {
    private final Long id;

    private final String slug;

    private final String name;

    public CategoriesMemento(Long id, String slug, String name) {
        this.id = id;
        this.slug = slug;
        this.name = name;
    }

}
