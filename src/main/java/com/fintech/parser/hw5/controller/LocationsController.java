package com.fintech.parser.hw5.controller;

import com.fintech.parser.hw5.dto.request.LocationRq;
import com.fintech.parser.hw5.dto.responce.LocationsRs;
import com.fintech.parser.hw5.service.LocationsService;
import com.fintech.parser.hw5.dto.responce.DeleteRs;
import com.fintech.timedstarter.annotation.TimedAnnotation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@TimedAnnotation
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/locations")
public class LocationsController {

    private final LocationsService locationsService;

    @GetMapping()
    public List<LocationsRs> getLocations() {
        return locationsService.getLocationsRs();
    }

    @GetMapping("/{id}")
    public LocationsRs getLocationById(@PathVariable String id) {
        return locationsService.getLocationRsById(id);
    }

    @PostMapping()
    public LocationsRs createLocation(@RequestBody LocationRq locationRq) {
        return locationsService.createLocation(locationRq);
    }

    @PutMapping("/{id}")
    public LocationsRs updateLocation(@PathVariable String id, @RequestBody LocationRq locationRq) {
        return locationsService.updateLocation(id, locationRq);
    }

    @DeleteMapping("/{id}")
    public DeleteRs deleteLocation(@PathVariable String id) {
        return locationsService.deleteLocation(id);
    }
}