package com.fintech.parser.hw5.repository;

import com.fintech.parser.hw5.model.Locations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@Lazy
public class LocationsRepository extends AppRepository<String, Locations> {

    public LocationsRepository() {
        log.info("Init LocationsRepository");
    }
}