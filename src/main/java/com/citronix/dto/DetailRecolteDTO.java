package com.citronix.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailRecolteDTO {
    private Long id;
    private Long arbreId;
    private Long recolteId;
    private double quantite;
}
