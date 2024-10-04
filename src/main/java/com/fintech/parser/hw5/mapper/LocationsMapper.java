package com.fintech.parser.hw5.mapper;

import com.fintech.parser.hw5.dto.request.LocationRq;
import com.fintech.parser.hw5.dto.responce.LocationsRs;
import com.fintech.parser.hw5.model.Locations;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationsMapper {

    LocationsMapper INSTANCE = getMapper(LocationsMapper.class);

    LocationsRs toDto(Locations locations);

    Locations toEntity(LocationRq locationRq);
}
