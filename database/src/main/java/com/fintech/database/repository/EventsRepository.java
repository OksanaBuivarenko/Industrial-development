package com.fintech.database.repository;

import com.fintech.database.entity.Events;
import com.fintech.database.entity.Locations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EventsRepository extends JpaRepository<Events, Long>, JpaSpecificationExecutor<Events> {

    @Query("SELECT events FROM Events events JOIN FETCH events.locations WHERE events.id = :id")
    Optional<Events> find(@Param("id") Long id);
}
