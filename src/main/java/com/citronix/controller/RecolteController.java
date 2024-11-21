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
    public RecolteDTO creerRecolte(@RequestParam Long champId,
                                   @RequestParam String saisonStr,
                                   @RequestParam LocalDate dateRecolte,
                                   @RequestParam List<Long> arbreIds) {
        return recolteService.creerRecolte(champId, saisonStr, dateRecolte, arbreIds);
    }

    @GetMapping("/saison/{saison}")
    public List<Recolte> getRecoltesBySaison(@PathVariable Saison saison) {
        return recolteService.getRecoltesBySaison(saison);
    }

}
