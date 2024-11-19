package com.citronix.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Champ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "0.1", message = "La superficie doit être au minimum de 0.1 hectare.")
    private Double superficie;

    @ManyToOne
    @JoinColumn(name = "ferme_id", nullable = false)
    private Ferme ferme;

    @OneToMany(mappedBy = "champ", cascade = CascadeType.ALL)
    private List<Arbre> arbres;
}
