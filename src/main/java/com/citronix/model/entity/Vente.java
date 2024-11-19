package com.citronix.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String client;

    @NotNull
    private Double prixUnitaire;

    @NotNull
    private LocalDate dateVente;

    @ManyToOne
    @JoinColumn(name = "recolte_id", nullable = false)
    private Recolte recolte;

    @Transient
    public Double calculerRevenu() {
        return prixUnitaire * recolte.getQuantiteTotale();
    }
}
