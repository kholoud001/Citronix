package com.citronix.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;



import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Arbre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate datePlantation;

    private int age;

    @ManyToOne
    @JoinColumn(name = "champ_id", nullable = false)
    @JsonBackReference
    private Champ champ;


    @OneToMany(mappedBy = "arbre", cascade = CascadeType.ALL)
    private List<DetailRecolte> recoltes;

    @Transient
    public double calculerProductivite() {
        if (age < 3) return 2.5;
        if (age <= 10) return 12.0;
        return 20.0;
    }

    @Transient
    public boolean estDansLaBonnePeriode() {
        // Les arbres doivent être plantés entre mars et mai
        int mois = datePlantation.getMonthValue();
        return mois >= 3 && mois <= 5;
    }

    @Transient
    public boolean estProductif() {
        // Vérifie si l'arbre est productif (moins de 20 ans)
        return age <= 20;
    }

    @Transient
    public boolean estDensiteValide() {
        // Vérifie que la densité des arbres est respectée (100 arbres par hectare)
        return champ.getArbres().size() <= champ.getSuperficie() * 100;
    }


    public int calculerAge() {
        return LocalDate.now().getYear() - datePlantation.getYear();
    }
}
