package com.fintech.parser.hw5.model;

import com.fintech.parser.hw5.service.memento.LocationsMemento;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Locations {

    private String slug;

    private String name;

    public LocationsMemento save() {
        return new LocationsMemento(slug, name);
    }

    public Locations restore(LocationsMemento memento) {
        setName(memento.getName());
        setSlug(memento.getSlug());
        return this;
    }
}