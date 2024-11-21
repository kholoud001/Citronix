package com.citronix.dto;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VenteDTO {
    private Long id;
    private String client;
    private Double prixUnitaire;
    private LocalDate dateVente;
    private Long recolteId;
//    private Double revenu;
}
