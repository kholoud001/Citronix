package com.citronix.controller;

import com.citronix.dto.ArbreDTO;
import com.citronix.service.ArbreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/arbre")
public class ArbreController {

    @Autowired
    private ArbreService arbreService;

    @PostMapping("/{champId}")
    public ResponseEntity<ArbreDTO> createArbre(@PathVariable Long champId,
                                                @RequestBody @Valid ArbreDTO arbreDTO) {
        ArbreDTO createdArbre = arbreService.createArbre(champId, arbreDTO);
        return ResponseEntity.ok(createdArbre);
    }
}
