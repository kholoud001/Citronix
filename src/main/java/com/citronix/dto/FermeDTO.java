package com.citronix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;


import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FermeDTO {
    private Long id;
    private String nom;
    private String localisation;
    private Double superficie;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreation;

    private List<ChampDTO> champs;
}
