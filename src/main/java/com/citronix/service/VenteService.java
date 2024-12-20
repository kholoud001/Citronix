package com.citronix.service;

import com.citronix.dto.VenteDTO;

import java.time.LocalDate;
import java.util.List;

public interface VenteService {

    VenteDTO creerVente(VenteDTO venteDTO);

    List<VenteDTO> obtenirVentesParRecolte(Long recolteId);

    VenteDTO updateVente(Long id, VenteDTO venteDTO);

    void deleteVente(Long id);
}
