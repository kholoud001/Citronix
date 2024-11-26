package com.citronix.controller;

import com.citronix.dto.FermeDTO;
import com.citronix.mapper.FermeMapper;
import com.citronix.model.entity.Ferme;
import com.citronix.service.FermeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/fermes")
public class FermeController {

    @Autowired
    private FermeService fermeService;

    @PostMapping
    public ResponseEntity<FermeDTO> createFerme(@Valid @RequestBody FermeDTO fermeDTO) {
        FermeDTO createdFerme = fermeService.createFerme(fermeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFerme);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FermeDTO> getFerme(@PathVariable Long id) {
        return fermeService.getFermeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FermeDTO> updateFerme(
            @PathVariable Long id,
            @Valid @RequestBody FermeDTO fermeDTO
    ) {
        FermeDTO updatedFerme = fermeService.updateFerme(id, fermeDTO);
        return ResponseEntity.ok(updatedFerme);
    }

    @DeleteMapping("/{id}")
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

    @GetMapping
    public ResponseEntity<List<FermeDTO>> getAllFermes() {
        List<FermeDTO> fermes = fermeService.getAllFermes();
        return ResponseEntity.ok(fermes);
    }

    @GetMapping("/pages")
    public ResponseEntity<Page<FermeDTO>> getAllFermesPages(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Page<FermeDTO> fermes = fermeService.getAllFermesWithPages(page, size);
        return ResponseEntity.ok(fermes);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchFermes(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String localisation,
            @RequestParam(required = false) Double superficie,
            @RequestParam(required = false) String dateCreation
    ) {
        LocalDate parsedDate = null;
        if (dateCreation != null && !dateCreation.isBlank()) {
            try {
                parsedDate = LocalDate.parse(dateCreation);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Date invalide. Utilisez le format 'YYYY-MM-DD'.");
            }
        }

        List<FermeDTO> result = fermeService.searchFerme(nom, localisation, superficie, parsedDate);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aucune ferme ne correspond aux critères de recherche.");
        }

        return ResponseEntity.ok(result);
    }

}
