package com.citronix.controller;

import com.citronix.dto.VenteDTO;
import com.citronix.service.VenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/ventes")
@RequiredArgsConstructor
public class VenteController {

    @Autowired
    private VenteService venteService;

    @PostMapping
    public ResponseEntity<VenteDTO> creerVente(@RequestBody VenteDTO venteDTO) {
        VenteDTO venteResponse = venteService.creerVente(venteDTO);
        return ResponseEntity.ok(venteResponse);
    }



}
