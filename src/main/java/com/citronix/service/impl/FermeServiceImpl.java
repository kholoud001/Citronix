package com.citronix.service.impl;

import com.citronix.model.entity.Ferme;
import com.citronix.repository.FermeRepository;
import com.citronix.service.FermeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
