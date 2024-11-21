package com.citronix.controller;

import com.citronix.dto.RecolteDTO;
import com.citronix.model.entity.Recolte;
import com.citronix.model.enums.Saison;
import com.citronix.service.RecolteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/recoltes")
@RequiredArgsConstructor
public class RecolteController {

    @Autowired
    private RecolteService recolteService;

    @PostMapping
    public ResponseEntity<RecolteDTO> creerRecolte(
            @RequestParam Long champId,
            @RequestParam String saison,
            @RequestParam String dateRecolte,  // au format "yyyy-MM-dd"
            @RequestParam List<Long> arbreIds
    ) {
        try {
            // Conversion de la date
            LocalDate date = LocalDate.parse(dateRecolte);

            // Appel à la méthode du service pour créer la récolte
            RecolteDTO recolteDTO = recolteService.creerRecolte(champId, saison, date, arbreIds);

            return ResponseEntity.status(HttpStatus.CREATED).body(recolteDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/saison/{saison}")
    public List<Recolte> getRecoltesBySaison(@PathVariable Saison saison) {
        return recolteService.getRecoltesBySaison(saison);
    }

}
