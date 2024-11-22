package com.citronix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChampDTO {
    private Long id;
    private Double superficie;
    //private Long fermeId;
}
