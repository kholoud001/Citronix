package com.citronix.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArbreDTO {
    private Long id;
    private String datePlantation;
    private int age;
    private Long champId;
}
