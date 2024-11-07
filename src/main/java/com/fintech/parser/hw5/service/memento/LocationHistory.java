package com.fintech.parser.hw5.service.memento;

import com.fintech.parser.hw5.model.Locations;

public interface LocationHistory {

    void save(Locations locations);

    void undo(Locations locations);

    void delete(String id);
}
