package com.citronix.controller;

import com.citronix.dto.ArbreDTO;
import com.citronix.service.ArbreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/arbre")
public class ArbreController {

    private final ArbreService arbreService;

    @Autowired
    public ArbreController(ArbreService arbreService) {
        this.arbreService = arbreService;
    }


    @PostMapping("/{champId}")
    public ResponseEntity<ArbreDTO> createArbre(@PathVariable Long champId,
                                                @RequestBody @Valid ArbreDTO arbreDTO) {
        ArbreDTO createdArbre = arbreService.createArbre(champId, arbreDTO);
        return ResponseEntity.ok(createdArbre);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArbreDTO> getArbre(@PathVariable Long id) {
        ArbreDTO arbreDTO = arbreService.getArbre(id);
        return ResponseEntity.ok(arbreDTO);
    }

    @DeleteMapping("/{arbreId}")
    public ResponseEntity<String> deleteArbre(@PathVariable Long arbreId) {
        arbreService.deleteArbre(arbreId);
        return ResponseEntity.ok("Arbre supprimé avec succès.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArbreDTO> updateArbre(
            @PathVariable("id") Long arbreId,
            @RequestBody ArbreDTO arbreDTO) {
        ArbreDTO updatedArbre = arbreService.updateArbre(arbreId, arbreDTO);

        return new ResponseEntity<>(updatedArbre, HttpStatus.OK);
    }

}
