package com.citronix.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;


import java.util.ArrayList;
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

    @JsonManagedReference
    @OneToMany(mappedBy = "champ", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Arbre> arbres = new ArrayList<>();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ferme_id", nullable = false)
    private Ferme ferme;

    @JsonManagedReference
    @OneToMany(mappedBy = "champ", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Recolte> recoltes = new ArrayList<>();


    public boolean isDensiteArbresValid() {
        // Vérifie la densité des arbres dans le champ (100 arbres par hectare)
        return arbres.size() <= superficie * 100;
    }
}
