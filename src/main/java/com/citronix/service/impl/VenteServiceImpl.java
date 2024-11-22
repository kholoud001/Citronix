package com.citronix.service.impl;

import com.citronix.dto.VenteDTO;
import com.citronix.mapper.VenteMapper;
import com.citronix.model.entity.Recolte;
import com.citronix.model.entity.Vente;
import com.citronix.repository.RecolteRepository;
import com.citronix.repository.VenteRepository;
import com.citronix.service.VenteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VenteServiceImpl implements VenteService {

    private  VenteRepository venteRepository;

    private  RecolteRepository recolteRepository;

    private VenteMapper venteMapper;


    @Override
    @Transactional
    public VenteDTO creerVente(VenteDTO venteDTO) {
        Recolte recolte = recolteRepository.findById(venteDTO.getRecolteId())
                .orElseThrow(() -> new RuntimeException("Récolte non trouvée"));

        Vente vente = venteMapper.toEntity(venteDTO);
        vente.setRecolte(recolte);

        vente = venteRepository.save(vente);

        VenteDTO savedVenteDTO = venteMapper.toDTO(vente);
        savedVenteDTO.setRevenu(vente.calculerRevenu());
        return savedVenteDTO;
    }


    @Override
    public List<VenteDTO> obtenirVentesParRecolte(Long recolteId) {
        List<Vente> ventes = venteRepository.findByRecolteId(recolteId);

        if (ventes.isEmpty()) {
            throw new IllegalArgumentException("Aucune vente trouvée pour la récolte avec l'ID : " + recolteId);
        }

        List<VenteDTO> venteDTOs = new ArrayList<>();
        for (Vente vente : ventes) {
            VenteDTO venteDTO = venteMapper.toDTO(vente);
            venteDTO.setRevenu(vente.calculerRevenu());
            venteDTOs.add(venteDTO);
        }
        return venteDTOs;
    }


    @Override
    public VenteDTO updateVente(Long id, VenteDTO venteDTO) {
        Vente vente = venteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vente non trouvée"));

        vente.setClient(venteDTO.getClient());
        vente.setPrixUnitaire(venteDTO.getPrixUnitaire());
        vente.setDateVente(venteDTO.getDateVente());

        Vente updatedVente = venteRepository.save(vente);

        VenteDTO updatedVenteDTO = venteMapper.toDTO(updatedVente);
        updatedVenteDTO.setRevenu(updatedVente.calculerRevenu());
        return updatedVenteDTO;
    }

    @Override
    @Transactional
    public void deleteVente(Long id) {
        Vente vente = venteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vente non trouvée"));
        venteRepository.delete(vente);
    }


}
