package com.citronix.service.impl;

import com.citronix.dto.ArbreDTO;
import com.citronix.mapper.ArbreMapper;
import com.citronix.model.entity.Arbre;
import com.citronix.model.entity.Champ;
import com.citronix.repository.ArbreRepository;
import com.citronix.repository.ChampRepository;
import com.citronix.service.ArbreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArbreServiceImpl implements ArbreService {

    @Autowired
    private ArbreRepository arbreRepository;

    @Autowired
    private ChampRepository champRepository;


    @Autowired
    private ArbreMapper arbreMapper;

    @Override
    @Transactional
    public ArbreDTO createArbre(Long champId, ArbreDTO arbreDTO) {
        // Récupérer le champ
        Champ champ = champRepository.findById(champId)
                .orElseThrow(() -> new IllegalArgumentException("Champ non trouvé"));

        // Mapper le DTO vers l'entité
        Arbre arbre = arbreMapper.toEntity(arbreDTO);

        // Définir le champ
        arbre.setChamp(champ);

        // Calculer et définir l'âge
        arbre.setAge(arbre.calculerAge());
        //arbre.setAge(arbre.calculerAge());


        // Valider la période de plantation
        if (!arbre.estDansLaBonnePeriode()) {
            throw new IllegalArgumentException("L'arbre doit être planté entre mars et mai.");
        }

        // Valider la densité
        if (!arbre.estDensiteValide()) {
            throw new IllegalArgumentException("La densité des arbres dépasse la limite autorisée.");
        }


        if (!arbre.estProductif()) {
            throw new IllegalArgumentException("Cet arbre a dépassé sa durée de vie maximale (20 ans).");
        }

        // Ajouter l'arbre au champ
        champ.getArbres().add(arbre);

//        champRepository.save(champ);
        arbre = arbreRepository.save(arbre);

        return arbreMapper.toDTO(arbre);
    }

    @Override
    public ArbreDTO getArbre(Long arbreId) {
        Arbre arbre = arbreRepository.findById(arbreId)
                .orElseThrow(() -> new IllegalArgumentException("Arbre non trouvé"));

        arbre.setAge(arbre.calculerAge());

        return arbreMapper.toDTO(arbre);
    }


}
