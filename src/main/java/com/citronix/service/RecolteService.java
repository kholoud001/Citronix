package com.citronix.service;

import com.citronix.dto.DetailRecolteDTO;
import com.citronix.dto.RecolteDTO;
import com.citronix.model.entity.Recolte;
import com.citronix.model.enums.Saison;

import java.time.LocalDate;
import java.util.List;

public interface RecolteService {
    RecolteDTO creerRecolte(Long champId, String saisonStr, LocalDate dateRecolte, List<Long> arbreIds);

    List<Recolte> getRecoltesBySaison(Saison saison);

    void supprimerRecolte(Long id);

    RecolteDTO mettreAJourRecolte(Long id, String saisonStr, LocalDate dateRecolte, List<Long> arbreIds);

    List<DetailRecolteDTO> getDetailsByRecolteId(Long recolteId);
}
