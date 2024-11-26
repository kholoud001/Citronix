package com.citronix.model.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ferme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="entrez un nom")
    @Size(max = 100)
    private String nom;

    @NotBlank
    private String localisation;

    @DecimalMin(value = "0.1", message = "La superficie doit être au minimum de 0.1 hectare.")
    private Double superficie;

    @NotNull
    private LocalDate dateCreation;


    @JsonManagedReference
    @OneToMany(mappedBy = "ferme", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Champ> champs = new ArrayList<>();


    public boolean isSuperficieValid() {
        // Vérifie si la somme des superficies des champs est inférieure à celle de la ferme
        double totalSuperficieChamps = 0.0;
        for (Champ champ : champs) {
            totalSuperficieChamps += champ.getSuperficie();
            System.out.println("superficie totale des champs = >"+totalSuperficieChamps);
        }
        return totalSuperficieChamps < this.superficie;
    }

    public boolean isDensiteArbresValid() {
        // Vérifie si la densité des arbres est respectée (100 arbres par hectare)
        for (Champ champ : champs) {
            if (!champ.isDensiteArbresValid()) {
                return false;
            }
        }
        return true;
    }

    public boolean isChampLimitValid() {
        // Vérifie si le nombre maximal de champs est respecté (10 champs max par ferme)
        return champs.size() <= 10;
    }

}

