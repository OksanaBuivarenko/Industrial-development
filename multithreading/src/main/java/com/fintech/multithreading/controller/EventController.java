package com.fintech.multithreading.controller;

import com.fintech.multithreading.dto.request.EventsRq;
import com.fintech.multithreading.dto.response.EventsRs;
import com.fintech.multithreading.service.EventsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventsService eventsService;

    @Operation(summary = "Получение событий на основе пользовательских пожеланий", operationId = "getEvents")
    @GetMapping()
    public List<EventsRs> getEvents(@Valid @ParameterObject EventsRq events) {
        return eventsService.getEventsRs(events);
    }
}
