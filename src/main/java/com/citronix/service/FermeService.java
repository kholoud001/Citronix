package com.citronix.service;

import com.citronix.dto.FermeDTO;
import com.citronix.model.entity.Ferme;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FermeService {


    @Transactional
    FermeDTO createFerme(FermeDTO fermeDTO);

    Optional<FermeDTO> getFermeById(Long id);

    @Transactional
    void supprimerFermeParId(Long id);

    @Transactional
    FermeDTO updateFerme(Long id, FermeDTO fermeDTO);

    List<FermeDTO> getAllFermes();


    List<FermeDTO> searchFerme(String name, String localisation, Double superficie, LocalDate dateCreation);
}
