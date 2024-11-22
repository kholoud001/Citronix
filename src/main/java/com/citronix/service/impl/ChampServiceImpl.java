package com.citronix.service.impl;

import com.citronix.dto.ChampDTO;
import com.citronix.mapper.ChampMapper;
import com.citronix.model.entity.Champ;
import com.citronix.model.entity.Ferme;
import com.citronix.repository.ChampRepository;
import com.citronix.repository.FermeRepository;
import com.citronix.service.ChampService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ChampServiceImpl implements ChampService {

    private ChampRepository champRepository;

    private FermeRepository fermeRepository;

    private ChampMapper champMapper;


    @Override
    public ChampDTO createChamp(long fermeId, ChampDTO champDTO) {
        Optional<Ferme> fermeOptional = fermeRepository.findById(fermeId);

        if (fermeOptional.isPresent()) {
            Ferme ferme = fermeOptional.get();

            if (ferme.getChamps().size() >= 10) {
                throw new IllegalStateException("La ferme ne peut contenir plus de 10 champs.");
            }

            double totalFieldArea = ferme.getChamps().stream()
                    .mapToDouble(Champ::getSuperficie)
                    .sum();
            System.out.println("totalFieldArea => "+totalFieldArea);
            if (champDTO.getSuperficie() + totalFieldArea > ferme.getSuperficie() / 2) {
                throw new IllegalStateException("La superficie totale des champs ne peut pas dépasser 50% de la superficie de la ferme.");
            }

            if (champDTO.getSuperficie() < 0.1) {
                throw new IllegalStateException("La superficie minimale d'un champ est de 0.1 hectare.");
            }

            Champ champ = champMapper.toEntity(champDTO);
            champ.setFerme(ferme);
            Champ savedChamp = champRepository.save(champ);

            return champMapper.toDTO(savedChamp);
        } else {
            throw new IllegalStateException("Ferme non trouvée avec l'ID " + fermeId);
        }
    }

    @Override
    public ChampDTO getChamp(Long champId) {
        Champ champ = champRepository.findById(champId)
                .orElseThrow(() -> new IllegalArgumentException("Champ non trouvé"));

        ChampDTO champDTO = champMapper.toDTO(champ);

        return champDTO;
    }

    @Override
    public void deleteChamp(Long champId) {
        if (!champRepository.existsById(champId)) {
            throw new IllegalArgumentException("Le champ avec l'ID " + champId + " n'existe pas.");
        }
        champRepository.deleteById(champId);
    }

    @Override
    public ChampDTO updateChamp(Long champId, ChampDTO champDTO) {
        Champ existingChamp = champRepository.findById(champId)
                .orElseThrow(() -> new IllegalArgumentException("Champ non trouvé avec l'ID " + champId));

        // Vérifiez les contraintes ici (par exemple, superficie minimale, maximale, etc.)
        if (champDTO.getSuperficie() < 0.1) {
            throw new IllegalStateException("La superficie minimale d'un champ est de 0.1 hectare.");
        }

        existingChamp.setSuperficie(champDTO.getSuperficie());

        Champ updatedChamp = champRepository.save(existingChamp);

        return champMapper.toDTO(updatedChamp);
    }


}
