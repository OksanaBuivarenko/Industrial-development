package com.fintech.interaction.mapper;

import com.fintech.interaction.dto.response.RatesRs;
import com.fintech.interaction.model.Valute;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ValuteMapper {

    @Mapping(target = "currency", source = "charCode")
    @Mapping(target = "rate", source = "vunitRate")
    RatesRs toDto(Valute valute);
}