package com.fintech.database.repository;

import com.fintech.database.entity.Events;
import com.fintech.database.entity.Events_;
import com.fintech.database.entity.Locations_;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.function.Supplier;

public interface EventsSpecification {
    static Specification<Events> equelsLocationName(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            root.fetch("locations", JoinType.LEFT);
            return cb.equal(root.join(Events_.locations.getName()).get(Locations_.name.getName()), value);
        };
    }

    static  Specification<Events> nameLike(String value) {
        return  (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            root.fetch("locations", JoinType.LEFT);
            return cb.like(root.get(Events_.name), "%" + value + "%");
        };
    }

    static Specification<Events> lessEqDateTo(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            root.fetch("locations", JoinType.LEFT);
            return cb.lessThanOrEqualTo(root.get(Events_.dates.getName()), value);
        };
    }

    static Specification<Events> greaterEqDateFrom(LocalDate value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            root.fetch("locations", JoinType.LEFT);
            return cb.greaterThanOrEqualTo(root.get(Events_.dates.getName()), value);
        };
    }
}