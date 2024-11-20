package com.citronix.service.impl;

import com.citronix.model.entity.Ferme;
import com.citronix.repository.FermeRepository;
import com.citronix.repository.specification.FermeSpecification;
import com.citronix.service.FermeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FermeServiceImpl implements FermeService {
    @Autowired
    private FermeRepository fermeRepository;



    @Transactional
    @Override
    public Ferme createFerme(Ferme ferme) {
        if (!ferme.isSuperficieValid()) {
            throw new IllegalArgumentException("La superficie totale des champs dépasse la superficie de la ferme.");
        }
        if (!ferme.isDensiteArbresValid()) {
            throw new IllegalArgumentException("La densité des arbres n'est pas respectée.");
        }
        if (!ferme.isChampLimitValid()) {
            throw new IllegalArgumentException("Le nombre de champs dépasse la limite autorisée.");
        }
        return fermeRepository.save(ferme);
    }


    @Override
    public Optional<Ferme> getFermeById(Long id) {
        return fermeRepository.findById(id);
    }

    @Override
    public Ferme updateFerme(Long id, Ferme ferme) {
        Optional<Ferme> optionalFerme = fermeRepository.findById(id);

        if (optionalFerme.isPresent()) {
            Ferme existingFerme = optionalFerme.get();
            existingFerme.setNom(ferme.getNom());
            existingFerme.setLocalisation(ferme.getLocalisation());
            existingFerme.setSuperficie(ferme.getSuperficie());
            existingFerme.setDateCreation(ferme.getDateCreation());
            return fermeRepository.save(existingFerme);
        } else {
            throw new RuntimeException("Ferme not found");
        }
    }

    @Override
    public List<Ferme> searchFerme(String nom, String localisation, Double superficieMin, Double superficieMax) {
        return fermeRepository.findAll(FermeSpecification.searchFerme(nom, localisation, superficieMin, superficieMax));
    }

}
