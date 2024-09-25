package com.fintech.parser.hw5.service;

import com.fintech.parser.hw5.dto.request.LocationRq;
import com.fintech.parser.hw5.dto.responce.DeleteRs;
import com.fintech.parser.hw5.dto.responce.LocationsRs;
import com.fintech.parser.hw5.model.Locations;

import java.util.List;

public interface LocationsService {

    List<LocationsRs> getLocationsRs();

    LocationsRs getLocationRsById(String id);

    LocationsRs createLocation(LocationRq locationRq);

    LocationsRs updateLocation(String id, LocationRq locationRq);

    DeleteRs deleteLocation(String id);

    void save(Locations locations);
}
