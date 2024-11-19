package com.citronix.model.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
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

    @NotBlank
    @Size(max = 100)
    private String nom;

    @NotBlank
    private String localisation;

    @DecimalMin(value = "0.1", message = "La superficie doit être au minimum de 0.1 hectare.")
    private Double superficie;

    @NotNull
    private LocalDate dateCreation;

    @OneToMany(mappedBy = "ferme", cascade = CascadeType.ALL)
    private List<Champ> champs;
}
