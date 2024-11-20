package com.citronix.dto;

import com.citronix.model.enums.Saison;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecolteDTO {
    private Long id;
    private Saison saison;
    private String dateRecolte;
    private Double quantiteTotale;
    private Long champId;
    private List<DetailRecolteDTO> details;

}
