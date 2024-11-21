package com.citronix.service.impl;

import com.citronix.dto.RecolteDTO;
import com.citronix.mapper.RecolteMapper;
import com.citronix.model.entity.Arbre;
import com.citronix.model.entity.Champ;
import com.citronix.model.entity.DetailRecolte;
import com.citronix.model.entity.Recolte;
import com.citronix.model.enums.Saison;
import com.citronix.repository.ArbreRepository;
import com.citronix.repository.ChampRepository;
import com.citronix.repository.RecolteRepository;
import com.citronix.service.RecolteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecolteServiceImpl implements RecolteService {

    @Autowired
    private  RecolteRepository recolteRepository;

    @Autowired
    private  ArbreRepository arbreRepository;

    @Autowired
    private  ChampRepository champRepository;

    @Autowired
    private RecolteMapper recolteMapper;

    @Override
    public RecolteDTO creerRecolte(Long champId, String saisonStr, LocalDate dateRecolte, List<Long> arbreIds) {
        // Vérification du champ
        Champ champ = champRepository.findById(champId)
                .orElseThrow(() -> new IllegalArgumentException("Champ non trouvé avec l'ID : " + champId));

        // Conversion de la chaîne en enum Saison
        Saison saison;
        try {
            saison = Saison.valueOf(saisonStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Saison invalide. Les saisons valides sont : PRINTEMPS, ETE, AUTOMNE, HIVER.");
        }

        // Vérification si une récolte existe déjà pour cette saison et ce champ
        boolean recolteExistante = recolteRepository.existsByChampAndSaison(champ, saison);
        if (recolteExistante) {
            throw new IllegalArgumentException("Une récolte existe déjà pour la saison " + saison + " dans ce champ.");
        }

        // Création de la récolte
        Recolte recolte = Recolte.builder()
                .saison(saison)
                .dateRecolte(dateRecolte)
                .champ(champ)
                .quantiteTotale(0.0)
                .details(new ArrayList<>()) // initialisation de la liste des détails
                .build();

        // Calcul de la quantité totale et ajout des détails
        double quantiteTotale = 0.0;

        // Boucle sur les arbres pour créer les détails de récolte
        for (Long arbreId : arbreIds) {
            Arbre arbre = arbreRepository.findById(arbreId)
                    .orElseThrow(() -> new IllegalArgumentException("Arbre non trouvé avec l'ID : " + arbreId));

            // Vérification de la productivité de l'arbre
            if (!arbre.estProductif()) {
                throw new IllegalArgumentException("L'arbre avec l'ID " + arbreId + " n'est pas productif et ne peut pas être inclus.");
            }

            // Calcul de la productivité de l'arbre
            double quantite = arbre.calculerProductivite();

            // Création du détail de récolte
            DetailRecolte detail = DetailRecolte.builder()
                    .recolte(recolte) // Lier le détail à la récolte
                    .arbre(arbre) // Lier le détail à l'arbre
                    .quantiteRecoltee(quantite)
                    .build();

            // Ajouter le détail à la liste des détails de la récolte
            recolte.getDetails().add(detail);
            quantiteTotale += quantite;
        }

        // Mise à jour de la quantité totale de la récolte
        recolte.setQuantiteTotale(quantiteTotale);

        // Enregistrer la récolte dans la base de données et retourner le DTO
        Recolte savedRecolte = recolteRepository.save(recolte);

        // Retourner le DTO de la récolte enregistrée
        return recolteMapper.toDTO(savedRecolte);
    }

    @Override
    public List<Recolte> getRecoltesBySaison(Saison saison) {
        return recolteRepository.findBySaison(saison);
    }

}
