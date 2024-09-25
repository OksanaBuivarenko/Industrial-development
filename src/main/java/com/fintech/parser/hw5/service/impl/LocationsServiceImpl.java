package com.fintech.parser.hw5.service.impl;

import com.fintech.parser.hw5.dto.request.LocationRq;
import com.fintech.parser.hw5.dto.responce.DeleteRs;
import com.fintech.parser.hw5.dto.responce.LocationsRs;
import com.fintech.parser.hw5.exception.ObjectAlreadyExistsException;
import com.fintech.parser.hw5.exception.ObjectNotFoundException;
import com.fintech.parser.hw5.mapper.LocationsMapper;
import com.fintech.parser.hw5.model.Locations;
import com.fintech.parser.hw5.repository.LocationsRepository;
import com.fintech.parser.hw5.service.LocationsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LocationsServiceImpl implements LocationsService {

    private final LocationsRepository locationsRepository;

    @Autowired
    @Lazy
    public LocationsServiceImpl(LocationsRepository locationsRepository) {
        this.locationsRepository = locationsRepository;
    }

    @Override
    public List<LocationsRs> getLocationsRs() {
        return locationsRepository.findAll().stream().map(LocationsMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    public List<Locations> getLocations() {
        return locationsRepository.findAll();
    }

    @Override
    public LocationsRs getLocationRsById(String id) {
        return LocationsMapper.INSTANCE.toDto(getLocationById(id));
    }

    public Locations getLocationById(String id) {
        return locationsRepository.findById(id).orElseThrow(()
                -> new ObjectNotFoundException("locations", id));
    }

    @Override
    public LocationsRs createLocation(LocationRq locationRq) {
        for (Locations el: getLocations()) {
            if(el.getSlug().equals(locationRq.getSlug())) {
                throw new ObjectAlreadyExistsException("Locations with slug " + el.getSlug() + " already exists");
            }
            if(el.getName().equals(locationRq.getName())) {
                throw new ObjectAlreadyExistsException("Locations with name " + el.getName() + " already exists");
            }
        }
        Locations locations = LocationsMapper.INSTANCE.toEntity(locationRq);
        locationsRepository.save(locations.getSlug(), locations);
        log.debug("Locations with id " + locations.getSlug() + " create successfully");
        return LocationsMapper.INSTANCE.toDto(locations);
    }

    @Override
    public LocationsRs updateLocation(String id, LocationRq locationRq) {
        Locations location = getLocationById(id);
        if (!locationRq.getName().isEmpty() && !locationRq.getName().equals(location.getName())) {
            location.setName(location.getName());
            log.debug("Locations with id " + id + " update name");
        }
        if (!locationRq.getSlug().isEmpty()&& !locationRq.getSlug().equals(location.getSlug())) {
            log.warn("Locations slug " + id + " cannot be changed");
        }
        return LocationsMapper.INSTANCE.toDto(locationsRepository.update(location.getSlug(), location));
    }

    @Override
    public DeleteRs deleteLocation(String id) {
        if (locationsRepository.containsId(id)) {
            locationsRepository.delete(id);
            log.debug("Locations with id " + id + " delete successfully");
            return DeleteRs.builder().message("Locations with id " + id + " delete successfully").build();
        } else {
            log.warn("Unable to delete locations. Storage don't contains locations with id " + id);
            return DeleteRs.builder().message("Storage don't contains locations with id " + id).build();
        }
    }

    @Override
    public void save(Locations locations) {
        locationsRepository.save(locations.getSlug(), locations);
    }
}