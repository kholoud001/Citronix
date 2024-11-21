package com.citronix.service.impl;

import com.citronix.dto.VenteDTO;
import com.citronix.model.entity.Recolte;
import com.citronix.model.entity.Vente;
import com.citronix.repository.RecolteRepository;
import com.citronix.repository.VenteRepository;
import com.citronix.service.VenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VenteServiceImpl implements VenteService {


    @Autowired
    private  VenteRepository venteRepository;

    @Autowired
    private  RecolteRepository recolteRepository;

    @Override
    public VenteDTO creerVente(VenteDTO venteDTO) {
        Recolte recolte = recolteRepository.findById(venteDTO.getRecolteId())
                .orElseThrow(() -> new RuntimeException("Récolte non trouvée"));

        Vente vente = Vente.builder()
                .client(venteDTO.getClient())
                .prixUnitaire(venteDTO.getPrixUnitaire())
                .dateVente(venteDTO.getDateVente())
                .recolte(recolte)
                .build();

        vente = venteRepository.save(vente);

        return new VenteDTO(vente.getId(), vente.getClient(), vente.getPrixUnitaire(), vente.getDateVente(), vente.getRecolte().getId());
    }


}
