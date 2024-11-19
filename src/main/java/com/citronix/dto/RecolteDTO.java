package com.citronix.dto;

import com.citronix.model.enums.Saison;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecolteDTO {
    private Long id;
    private Saison saison;
    private String dateRecolte;
    private Double quantiteTotale;
}
