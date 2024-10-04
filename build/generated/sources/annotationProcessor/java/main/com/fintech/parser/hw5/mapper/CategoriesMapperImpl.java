package com.fintech.parser.hw5.mapper;

import com.fintech.parser.hw5.dto.request.CategoriesRq;
import com.fintech.parser.hw5.dto.responce.CategoriesRs;
import com.fintech.parser.hw5.model.Categories;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-04T11:49:02+0300",
    comments = "version: 1.6.2, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.8.jar, environment: Java 17.0.6 (Oracle Corporation)"
)
@Component
public class CategoriesMapperImpl implements CategoriesMapper {

    @Override
    public CategoriesRs toDto(Categories categories) {
        if ( categories == null ) {
            return null;
        }

        CategoriesRs categoriesRs = new CategoriesRs();

        categoriesRs.setSlug( categories.getSlug() );
        categoriesRs.setName( categories.getName() );

        return categoriesRs;
    }

    @Override
    public Categories toEntity(CategoriesRq categoriesRq, Long id) {
        if ( categoriesRq == null && id == null ) {
            return null;
        }

        Categories categories = new Categories();

        if ( categoriesRq != null ) {
            categories.setSlug( categoriesRq.getSlug() );
            categories.setName( categoriesRq.getName() );
        }
        categories.setId( id );

        return categories;
    }
}
