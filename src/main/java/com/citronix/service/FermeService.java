package com.citronix.service;

import com.citronix.model.entity.Ferme;
import org.springframework.transaction.annotation.Transactional;

public interface FermeService {
    @Transactional
    Ferme createFerme(Ferme ferme);
}
