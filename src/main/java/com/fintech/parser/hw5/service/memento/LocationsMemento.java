package com.fintech.parser.hw5.service.memento;

import lombok.Getter;

@Getter
public class LocationsMemento {

    private final String slug;

    private final String name;

    public LocationsMemento(String slug, String name) {
        this.slug = slug;
        this.name = name;
    }
}
