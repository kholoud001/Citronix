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

@RestController
@RequestMapping("/api/fermes")
public class FermeController {
    @Autowired
    private FermeService fermeService;

    @Autowired
    private ChampService champService;



    @PostMapping
    public Ferme createFerme(@RequestBody Ferme ferme) {
        return fermeService.createFerme(ferme);
    }

}
