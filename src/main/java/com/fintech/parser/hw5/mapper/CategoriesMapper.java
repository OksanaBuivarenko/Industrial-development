package com.fintech.parser.hw5.mapper;

import com.fintech.parser.hw5.dto.request.CategoriesRq;
import com.fintech.parser.hw5.dto.responce.CategoriesRs;
import com.fintech.parser.hw5.model.Categories;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoriesMapper {

    CategoriesMapper INSTANCE = getMapper(CategoriesMapper.class);

    CategoriesRs toDto(Categories categories);

    @Mapping(target = "slug", source = "categoriesRq.slug")
    @Mapping(target = "name", source = "categoriesRq.name")
    @Mapping(target = "id", source = "id")
    Categories toEntity(CategoriesRq categoriesRq, Long id);
}
