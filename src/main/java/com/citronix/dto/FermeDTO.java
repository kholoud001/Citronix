package com.citronix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FermeDTO {
    private Long id;
    private String nom;
    private String localisation;
    private Double superficie;
    private String dateCreation;
}
