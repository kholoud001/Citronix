package com.citronix.service.impl;

import com.citronix.dto.VenteDTO;
import com.citronix.model.entity.Recolte;
import com.citronix.model.entity.Vente;
import com.citronix.repository.RecolteRepository;
import com.citronix.repository.VenteRepository;
import com.citronix.service.VenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        // Création de la vente à partir du DTO
        Vente vente = Vente.builder()
                .client(venteDTO.getClient())
                .prixUnitaire(venteDTO.getPrixUnitaire())
                .dateVente(venteDTO.getDateVente())
                .recolte(recolte)
                .build();

        // Sauvegarde de la vente
        vente = venteRepository.save(vente);

        // Calcul du revenu en utilisant la méthode calculerRevenu
        Double revenu = vente.calculerRevenu();

        // Retourner un DTO avec le revenu calculé
        return new VenteDTO(vente.getId(), vente.getClient(), vente.getPrixUnitaire(), vente.getDateVente(), vente.getRecolte().getId(), revenu);
    }




    @Override
    public List<VenteDTO> obtenirVentesParRecolte(Long recolteId) {
        List<Vente> ventes = venteRepository.findByRecolteId(recolteId);

        List<VenteDTO> venteDTOList = new ArrayList<>();

        for (Vente vente : ventes) {
            Double revenu = vente.calculerRevenu();

            VenteDTO venteDTO = new VenteDTO();
            venteDTO.setId(vente.getId());
            venteDTO.setClient(vente.getClient());
            venteDTO.setPrixUnitaire(vente.getPrixUnitaire());
            venteDTO.setDateVente(vente.getDateVente());
            venteDTO.setRecolteId(vente.getRecolte().getId());
            venteDTO.setRevenu(revenu);
            venteDTOList.add(venteDTO);
        }

        return venteDTOList;
    }



    @Override
    public VenteDTO updateVente(Long id, VenteDTO venteDTO) {
        Vente vente = venteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vente non trouvée"));

        vente.setClient(venteDTO.getClient());
        vente.setPrixUnitaire(venteDTO.getPrixUnitaire());
        vente.setDateVente(venteDTO.getDateVente());

        // Associer la récolte
        // Si nécessaire, vous pouvez récupérer la récolte par ID et l'assigner
        // vente.setRecolte(recolteRepository.findById(venteDTO.getRecolteId()).orElseThrow(() -> new RuntimeException("Recolte non trouvée")));

        Vente updatedVente = venteRepository.save(vente);
        Double revenu = updatedVente.calculerRevenu();


        return new VenteDTO(updatedVente.getId(), updatedVente.getClient(),
                updatedVente.getPrixUnitaire(), updatedVente.getDateVente(),
                updatedVente.getRecolte().getId(), revenu);
    }


    @Override
    @Transactional
    public void deleteVente(Long id) {
        Vente vente = venteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vente non trouvée"));

        venteRepository.delete(vente);
    }


}
