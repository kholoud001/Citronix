package com.citronix.model.entity;

import com.citronix.model.enums.Saison;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recolte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Saison saison;

    @NotNull
    private LocalDate dateRecolte;

    @NotNull
    @PastOrPresent(message="la date de r2colte ne doit pas être dans le future")
    private Double quantiteTotale;

    @ManyToOne
    @JoinColumn(name = "champ_id", nullable = false)
    private Champ champ;

    @OneToMany(mappedBy = "recolte", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<DetailRecolte> details;



}
