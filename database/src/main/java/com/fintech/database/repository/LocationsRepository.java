package com.fintech.database.repository;

import com.fintech.database.entity.Locations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LocationsRepository extends JpaRepository<Locations, Long> {

    @Query("SELECT locations FROM Locations locations JOIN FETCH locations.events WHERE locations.id = :id")
    Optional<Locations> find(@Param("id") Long id);

    @Query("SELECT locations FROM Locations locations JOIN FETCH locations.events WHERE locations.name = :name")
    Optional<Locations> findByName(String name);

    @Query("SELECT locations FROM Locations locations JOIN FETCH locations.events WHERE locations.slug = :slug")
    Optional<Locations> findBySlug(String slug);
}
