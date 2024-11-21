package com.citronix.service.impl;

import com.citronix.dto.VenteDTO;
import com.citronix.model.entity.Recolte;
import com.citronix.model.entity.Vente;
import com.citronix.repository.RecolteRepository;
import com.citronix.repository.VenteRepository;
import com.citronix.service.VenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<VenteDTO> obtenirVentesParRecolte(Long recolteId) {
        List<Vente> ventes = venteRepository.findByRecolteId(recolteId);

        List<VenteDTO> venteDTOList = new ArrayList<>();

        for (Vente vente : ventes) {
            VenteDTO venteDTO = new VenteDTO();
            venteDTO.setId(vente.getId());
            venteDTO.setClient(vente.getClient());
            venteDTO.setPrixUnitaire(vente.getPrixUnitaire());
            venteDTO.setDateVente(vente.getDateVente());
            venteDTO.setRecolteId(vente.getRecolte().getId());
            venteDTOList.add(venteDTO);
        }

        return venteDTOList;
    }


}
