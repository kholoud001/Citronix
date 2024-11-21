package com.citronix.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArbreDTO {
    private Long id;
    private LocalDate datePlantation;
    private int age;
    private Long champId;
    //private List<DetailRecolteDTO> details;

    private boolean dansLaBonnePeriode;

}
