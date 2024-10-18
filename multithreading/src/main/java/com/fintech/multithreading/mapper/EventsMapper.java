package com.fintech.multithreading.mapper;

import com.fintech.multithreading.dto.response.EventsRs;
import com.fintech.multithreading.model.Events;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventsMapper {

    EventsRs toDto(Events events);
}