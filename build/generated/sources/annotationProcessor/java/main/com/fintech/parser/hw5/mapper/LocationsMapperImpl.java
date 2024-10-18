package com.fintech.parser.hw5.mapper;

import com.fintech.parser.hw5.dto.request.LocationRq;
import com.fintech.parser.hw5.dto.responce.LocationsRs;
import com.fintech.parser.hw5.model.Locations;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-09T17:55:39+0300",
    comments = "version: 1.6.2, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.8.jar, environment: Java 17.0.6 (Oracle Corporation)"
)
@Component
public class LocationsMapperImpl implements LocationsMapper {

    @Override
    public LocationsRs toDto(Locations locations) {
        if ( locations == null ) {
            return null;
        }

        LocationsRs locationsRs = new LocationsRs();

        locationsRs.setSlug( locations.getSlug() );
        locationsRs.setName( locations.getName() );

        return locationsRs;
    }

    @Override
    public Locations toEntity(LocationRq locationRq) {
        if ( locationRq == null ) {
            return null;
        }

        Locations locations = new Locations();

        locations.setSlug( locationRq.getSlug() );
        locations.setName( locationRq.getName() );

        return locations;
    }
}
