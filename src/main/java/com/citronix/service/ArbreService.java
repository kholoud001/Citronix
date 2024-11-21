package com.citronix.service;

import com.citronix.dto.ArbreDTO;
import com.citronix.model.entity.Arbre;
import org.springframework.transaction.annotation.Transactional;

public interface ArbreService {
    ArbreDTO createArbre(Long champId, ArbreDTO arbreDTO);

    ArbreDTO getArbre(Long arbreId);

    void deleteArbre(Long arbreId);

    @Transactional
    ArbreDTO updateArbre(Long arbreId, ArbreDTO arbreDTO);
}
