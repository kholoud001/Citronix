package com.citronix.service.impl;

import com.citronix.dto.DetailRecolteDTO;
import com.citronix.dto.RecolteDTO;
import com.citronix.mapper.RecolteMapper;
import com.citronix.model.entity.Arbre;
import com.citronix.model.entity.Champ;
import com.citronix.model.entity.DetailRecolte;
import com.citronix.model.entity.Recolte;
import com.citronix.model.enums.Saison;
import com.citronix.repository.ArbreRepository;
import com.citronix.repository.ChampRepository;
import com.citronix.repository.DetailRecolteRepository;
import com.citronix.repository.RecolteRepository;
import com.citronix.service.RecolteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;


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
    private DetailRecolteRepository detailRecolteRepository;


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


    @Override
    public void supprimerRecolte(Long id) {
        Recolte recolte = recolteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recolte non trouvée avec l'ID : " + id));

        recolteRepository.delete(recolte);
    }

    @Override
    public RecolteDTO mettreAJourRecolte(Long id, String saisonStr, LocalDate dateRecolte, List<Long> arbreIds) {
        // Find existing Recolte
        Recolte recolte = recolteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recolte non trouvée avec l'ID : " + id));

        // Update fields
        Saison saison;
        try {
            saison = Saison.valueOf(saisonStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Saison invalide.");
        }

        // Check if there's another harvest for this champ and saison (if applicable)
        Champ champ = recolte.getChamp();
        boolean recolteExistante = recolteRepository.existsByChampAndSaison(champ, saison);
        if (recolteExistante && !recolte.getSaison().equals(saison)) {
            throw new IllegalArgumentException("Une récolte existe déjà pour cette saison.");
        }

        // Update the Recolte's details
        recolte.setSaison(saison);
        recolte.setDateRecolte(dateRecolte);

        // Update the details (assuming same arbreIds)
        recolte.getDetails().clear();  // Clear existing details
        double quantiteTotale = 0.0;

        for (Long arbreId : arbreIds) {
            Arbre arbre = arbreRepository.findById(arbreId)
                    .orElseThrow(() -> new IllegalArgumentException("Arbre non trouvé avec l'ID : " + arbreId));

            // Check productivity
            if (!arbre.estProductif()) {
                throw new IllegalArgumentException("L'arbre avec l'ID " + arbreId + " n'est pas productif.");
            }

            double quantite = arbre.calculerProductivite();
            DetailRecolte detail = DetailRecolte.builder()
                    .recolte(recolte)
                    .arbre(arbre)
                    .quantiteRecoltee(quantite)
                    .build();

            recolte.getDetails().add(detail);
            quantiteTotale += quantite;
        }

        recolte.setQuantiteTotale(quantiteTotale);

        // Save updated Recolte
        Recolte updatedRecolte = recolteRepository.save(recolte);

        return recolteMapper.toDTO(updatedRecolte);
    }


    @Override
    public List<DetailRecolteDTO> getDetailsByRecolteId(Long recolteId) {
        List<DetailRecolte> details = detailRecolteRepository.findByRecolteId(recolteId);

        return details.stream()
                .map(detail -> DetailRecolteDTO.builder()
                        .id(detail.getId())
                        .arbreId(detail.getArbre().getId())
                        .recolteId(detail.getRecolte().getId())
                        .quantite(detail.getQuantiteRecoltee())
                        .build())
                .collect(Collectors.toList());
    }


}
