package com.citronix.repository.specification;

import com.citronix.model.entity.Ferme;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class FermeSpecification {
    public static Specification<Ferme> searchFerme(String nom, String localisation, Double superficieMin, Double superficieMax) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (nom != null && !nom.isEmpty()) {
                predicate = builder.and(predicate, builder.like(root.get("nom"), "%" + nom + "%"));
            }

            if (localisation != null && !localisation.isEmpty()) {
                predicate = builder.and(predicate, builder.like(root.get("localisation"), "%" + localisation + "%"));
            }

            if (superficieMin != null) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get("superficie"), superficieMin));
            }

            if (superficieMax != null) {
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get("superficie"), superficieMax));
            }

            return predicate;
        };
    }
}
