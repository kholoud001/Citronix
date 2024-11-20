package com.citronix.service;

import com.citronix.dto.ArbreDTO;

public interface ArbreService {
    ArbreDTO createArbre(Long champId, ArbreDTO arbreDTO);
}
