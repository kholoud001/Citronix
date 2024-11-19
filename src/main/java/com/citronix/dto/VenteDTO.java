package com.citronix.dto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VenteDTO {
    private Long id;
    private String client;
    private Double prixUnitaire;
    private String dateVente;
    private Long recolteId;
}
