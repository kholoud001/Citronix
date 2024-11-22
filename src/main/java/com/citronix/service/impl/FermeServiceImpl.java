package com.citronix.service.impl;

import com.citronix.dto.FermeDTO;
import com.citronix.mapper.FermeMapper;
import com.citronix.model.entity.Ferme;
import com.citronix.repository.FermeRepository;
import com.citronix.service.FermeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class FermeServiceImpl implements FermeService {

    private FermeRepository fermeRepository;

    private FermeMapper fermeMapper;


    @Transactional
    @Override
    public FermeDTO createFerme(FermeDTO fermeDTO) {
        Ferme ferme = fermeMapper.toEntity(fermeDTO);

        if (!ferme.isSuperficieValid()) {
            throw new IllegalArgumentException("La superficie totale des champs dépasse la superficie de la ferme.");
        }
        if (!ferme.isDensiteArbresValid()) {
            throw new IllegalArgumentException("La densité des arbres n'est pas respectée.");
        }
        if (!ferme.isChampLimitValid()) {
            throw new IllegalArgumentException("Le nombre de champs dépasse la limite autorisée.");
        }

        Ferme savedFerme = fermeRepository.save(ferme);
        return fermeMapper.toDTO(savedFerme);
    }

    @Override
    public Optional<FermeDTO> getFermeById(Long id) {
        return fermeRepository.findById(id)
                .map(fermeMapper::toDTO);
    }

    @Transactional
    @Override
    public void supprimerFermeParId(Long id) {
        Ferme ferme = fermeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ferme non trouvée"));
        fermeRepository.delete(ferme);
    }

    @Transactional
    @Override
    public FermeDTO updateFerme(Long id, FermeDTO fermeDTO) {
        Ferme existingFerme = fermeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ferme not found"));

        existingFerme.setNom(fermeDTO.getNom());
        existingFerme.setLocalisation(fermeDTO.getLocalisation());
        existingFerme.setSuperficie(fermeDTO.getSuperficie());
        existingFerme.setDateCreation(fermeDTO.getDateCreation());

        Ferme updatedFerme = fermeRepository.save(existingFerme);
        return fermeMapper.toDTO(updatedFerme);
    }


    @Override
    public List<FermeDTO> getAllFermes() {
        return fermeMapper.toDTOs(fermeRepository.findAll());
    }

    @Override
    public List<FermeDTO> searchFerme(String nom, String localisation, Double superficie, LocalDate dateCreation) {
        List<Ferme> fermes = fermeRepository.search(nom, localisation, superficie, dateCreation);
        return fermeMapper.toDTOs(fermes);
    }





}
