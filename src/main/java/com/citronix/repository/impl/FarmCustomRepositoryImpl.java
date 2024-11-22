package com.citronix.repository.impl;

import com.citronix.model.entity.Ferme;
import com.citronix.repository.FarmCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FarmCustomRepositoryImpl implements FarmCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Ferme> search(String nom, String localisation, Double superficie, LocalDate dateCreation) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ferme> query = cb.createQuery(Ferme.class);
        Root<Ferme> root = query.from(Ferme.class);

        List<Predicate> predicates = new ArrayList<>();

        if (nom != null && !nom.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("nom")), "%" + nom.toLowerCase() + "%"));
        }
        if (localisation != null && !localisation.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("localisation")), "%" + localisation.toLowerCase() + "%"));
        }
        if (superficie != null) {
            predicates.add(cb.equal(root.get("superficie"), superficie));
        }
        if (dateCreation != null) {
            predicates.add(cb.equal(root.get("dateCreation"), dateCreation));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }


}
