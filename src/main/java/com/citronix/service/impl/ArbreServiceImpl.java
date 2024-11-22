package com.citronix.service.impl;

import com.citronix.dto.ArbreDTO;
import com.citronix.mapper.ArbreMapper;
import com.citronix.model.entity.Arbre;
import com.citronix.model.entity.Champ;
import com.citronix.repository.ArbreRepository;
import com.citronix.repository.ChampRepository;
import com.citronix.service.ArbreService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ArbreServiceImpl implements ArbreService {

    private ArbreRepository arbreRepository;

    private ChampRepository champRepository;

    private ArbreMapper arbreMapper;

    @Override
    @Transactional
    public ArbreDTO createArbre(Long champId, ArbreDTO arbreDTO) {
        Champ champ = champRepository.findById(champId)
                .orElseThrow(() -> new IllegalArgumentException("Champ non trouvé"));

        Arbre arbre = arbreMapper.toEntity(arbreDTO);

        arbre.setChamp(champ);

        arbre.setAge(arbre.calculerAge());

        if (!arbre.estDansLaBonnePeriode()) {
            throw new IllegalArgumentException("L'arbre doit être planté entre mars et mai.");
        }

        if (!arbre.estDensiteValide()) {
            throw new IllegalArgumentException("La densité des arbres dépasse la limite autorisée.");
        }

        if (!arbre.estProductif()) {
            throw new IllegalArgumentException("Cet arbre a dépassé sa durée de vie maximale (20 ans).");
        }

        champ.getArbres().add(arbre);

//        champRepository.save(champ);
        arbre = arbreRepository.save(arbre);

        return arbreMapper.toDTO(arbre);
    }

    @Override
    @Transactional
    public ArbreDTO getArbre(Long arbreId) {
        Arbre arbre = arbreRepository.findById(arbreId)
                .orElseThrow(() -> new IllegalArgumentException("Arbre non trouvé"));

        arbre.setAge(arbre.calculerAge());
        return arbreMapper.toDTO(arbre);
    }

    @Transactional
    @Override
    public void deleteArbre(Long arbreId) {
        Arbre arbre = arbreRepository.findById(arbreId)
                .orElseThrow(() -> new IllegalArgumentException("Arbre non trouvé"));

        arbreRepository.delete(arbre);
        arbreRepository.flush();
    }

    @Transactional
    @Override
    public ArbreDTO updateArbre(Long arbreId, ArbreDTO arbreDTO) {
        Arbre arbre = arbreRepository.findById(arbreId)
                .orElseThrow(() -> new IllegalArgumentException("Arbre not found"));

        arbre.setDatePlantation(arbreDTO.getDatePlantation());
        arbre.setAge(arbre.calculerAge());

        if (!arbre.estDansLaBonnePeriode()) {
            throw new IllegalArgumentException("L'arbre doit être planté entre mars et mai.");
        }

         Champ champ = champRepository.findById(arbreDTO.getChampId())
                .orElseThrow(() -> new IllegalArgumentException("Champ not found"));
        arbre.setChamp(champ);

        arbreRepository.save(arbre);

        ArbreDTO updatedArbreDTO = arbreMapper.toDTO(arbre);

        updatedArbreDTO.setDansLaBonnePeriode(arbre.estDansLaBonnePeriode());

        return updatedArbreDTO;
    }








}
