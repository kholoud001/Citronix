package com.citronix.controller;

import com.citronix.dto.FermeDTO;
import com.citronix.mapper.FermeMapper;
import com.citronix.model.entity.Ferme;
import com.citronix.service.FermeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/fermes")
public class FermeController {
    @Autowired
    private FermeService fermeService;

    @Autowired
    private FermeMapper fermeMapper;

    @PostMapping
    public ResponseEntity<FermeDTO> createFerme(@Valid @RequestBody FermeDTO fermeDTO) {
        if (fermeDTO.getChamps() == null) {
            fermeDTO.setChamps(new ArrayList<>());
        }
        Ferme ferme = fermeMapper.toEntity(fermeDTO);
        Ferme createdFerme = fermeService.createFerme(ferme);
        return ResponseEntity.status(201).body(fermeMapper.toDTO(createdFerme));
    }


    @GetMapping("/{id}")
    public ResponseEntity<FermeDTO> getFerme(@PathVariable Long id) {
        Ferme ferme = fermeService.getFermeById(id)
                .orElseThrow(() -> new RuntimeException("Ferme not found"));
        FermeDTO fermeDTO = fermeMapper.toDTO(ferme);
        return ResponseEntity.ok(fermeDTO);
    }


    @PutMapping("/{id}")
    public ResponseEntity<FermeDTO> updateFerme(
            @PathVariable Long id,
            @Valid @RequestBody FermeDTO fermeDTO
    ) {
        Ferme ferme = fermeMapper.toEntity(fermeDTO);
        Ferme updatedFerme = fermeService.updateFerme(id, ferme);
        FermeDTO updatedFermeDTO = fermeMapper.toDTO(updatedFerme);
        return ResponseEntity.ok(updatedFermeDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<List<FermeDTO>> searchFerme(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String localisation,
            @RequestParam(required = false) Double superficieMin,
            @RequestParam(required = false) Double superficieMax
    ) {
        List<Ferme> fermes = fermeService.searchFerme(nom, localisation, superficieMin, superficieMax);

        List<FermeDTO> fermeDTOs = fermeMapper.toDTOs(fermes);

        return ResponseEntity.ok(fermeDTOs);
    }

    @DeleteMapping("/supprime/{id}")
    public ResponseEntity<String> deleteFermeById(@PathVariable Long id) {
        try {
            fermeService.supprimerFermeParId(id);
            return ResponseEntity.ok("Ferme supprimée avec succès !");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne lors de la suppression.");
        }
    }




}
