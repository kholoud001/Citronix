package com.citronix.mapper;

import com.citronix.dto.FermeDTO;
import com.citronix.model.entity.Ferme;
import org.mapstruct.*;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring")
public interface FermeMapper {

    FermeDTO toDTO(Ferme ferme);

    @Mapping(target = "champs", source = "champs", defaultExpression = "java(new java.util.ArrayList<>())")
    Ferme toEntity(FermeDTO fermeDTO);

    List<FermeDTO> toDTOs(List<Ferme> fermes);

    List<Ferme> toEntities(List<FermeDTO> fermeDTOs);



    default String mapLocalDateToString(LocalDate date) {
        return date != null ? date.toString() : null;
    }

    default LocalDate mapStringToLocalDate(String date) {
        return date != null ? LocalDate.parse(date) : null;
    }

}
