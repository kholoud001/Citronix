package com.citronix.repository;

import com.citronix.model.entity.Ferme;

import java.time.LocalDate;
import java.util.List;

public interface FarmCustomRepository {
    List<Ferme> search (String name, String localisation, Double superficie, LocalDate dateCreation);
}
