package com.citronix.controller;

import com.citronix.dto.ChampDTO;
import com.citronix.model.entity.Champ;
import com.citronix.service.ChampService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/champs")
public class ChampController {

    @Autowired
    private ChampService champService;


    @PostMapping("/{fermeId}")
    public ResponseEntity<ChampDTO> addChamp(@PathVariable Long fermeId,
                                             @RequestBody @Valid ChampDTO champDTO) {
        ChampDTO createdChamp = champService.createChamp(fermeId, champDTO);
        return ResponseEntity.ok(createdChamp);
    }

    @GetMapping("/{champId}")
    public ResponseEntity<ChampDTO> getChamp(@PathVariable Long champId) {
        try {
            ChampDTO champDTO = champService.getChamp(champId);
            return new ResponseEntity<>(champDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChamp(@PathVariable Long id) {
        champService.deleteChamp(id);
        return ResponseEntity.ok("Champ supprimé avec succès.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChampDTO> updateChamp(
            @PathVariable Long id,
            @RequestBody ChampDTO champDTO) {
        ChampDTO updatedChamp = champService.updateChamp(id, champDTO);
        return ResponseEntity.ok(updatedChamp);
    }


}
