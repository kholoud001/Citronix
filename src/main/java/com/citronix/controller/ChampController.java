package com.citronix.controller;

import com.citronix.dto.ChampDTO;
import com.citronix.mapper.ChampMapper;
import com.citronix.model.entity.Champ;
import com.citronix.model.entity.Ferme;
import com.citronix.service.ChampService;
import com.citronix.service.FermeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/champs")
public class ChampController {

    @Autowired
    private ChampService champService;


    @PostMapping("/{fermeId}")
    public ResponseEntity<ChampDTO> addChamp(@PathVariable Long fermeId, @RequestBody @Valid ChampDTO champDTO) {
        ChampDTO createdChamp = champService.createChamp(fermeId, champDTO);
        return ResponseEntity.ok(createdChamp);
    }
}
