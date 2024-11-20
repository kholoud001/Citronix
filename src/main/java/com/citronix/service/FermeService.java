package com.citronix.service;

import com.citronix.model.entity.Ferme;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface FermeService {
    @Transactional
    Ferme createFerme(Ferme ferme);

    Optional<Ferme> getFermeById(Long id);

    Ferme updateFerme(Long id, Ferme ferme);

    List<Ferme> searchFerme(String nom, String localisation, Double superficieMin, Double superficieMax);
}
