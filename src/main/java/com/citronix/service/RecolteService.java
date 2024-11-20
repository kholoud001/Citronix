package com.citronix.service;

import com.citronix.dto.RecolteDTO;

import java.time.LocalDate;
import java.util.List;

public interface RecolteService {
    RecolteDTO creerRecolte(Long champId, String saisonStr, LocalDate dateRecolte, List<Long> arbreIds);
}
