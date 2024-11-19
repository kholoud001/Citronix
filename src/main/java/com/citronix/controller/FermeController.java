package com.citronix.controller;

import com.citronix.model.entity.Champ;
import com.citronix.model.entity.Ferme;
import com.citronix.service.ChampService;
import com.citronix.service.FermeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/fermes")
public class FermeController {
    @Autowired
    private FermeService fermeService;

    @PostMapping
    public Ferme createFerme(@RequestBody Ferme ferme) {
        return fermeService.createFerme(ferme);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ferme> getFerme(@PathVariable Long id) {
        Ferme ferme = fermeService.getFermeById(id)
                .orElseThrow(() -> new RuntimeException("Ferme not found"));
        return ResponseEntity.ok(ferme);
    }

    @PutMapping("/{id}")
    public Ferme updateFerme(@PathVariable Long id, @Valid @RequestBody Ferme ferme) {
        return fermeService.updateFerme(id, ferme);
    }

}
