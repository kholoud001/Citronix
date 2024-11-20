package com.citronix.repository.specification;

import com.citronix.model.entity.Ferme;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class FermeSpecification {
    public static Specification<Ferme> searchFerme(String nom, String localisation, Double superficieMin, Double superficieMax) {
        return new Specification<Ferme>() {

            @Override
            public Predicate toPredicate(Root<Ferme> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                // Initialize the predicate with a conjunction (AND operation)
                Predicate predicate = builder.conjunction();

                // Check if nom is provided and not empty
                if (nom != null && !nom.isEmpty()) {
                    predicate = builder.and(predicate, builder.like(root.get("nom"), "%" + nom + "%"));
                }

                // Check if localisation is provided and not empty
                if (localisation != null && !localisation.isEmpty()) {
                    predicate = builder.and(predicate, builder.like(root.get("localisation"), "%" + localisation + "%"));
                }

                // Check if superficieMin is provided
                if (superficieMin != null) {
                    predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get("superficie"), superficieMin));
                }

                // Check if superficieMax is provided
                if (superficieMax != null) {
                    predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get("superficie"), superficieMax));
                }

                return predicate;
            }
        };
    }
}