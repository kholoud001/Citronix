package com.citronix.mapper;

import com.citronix.dto.FermeDTO;
import com.citronix.model.entity.Ferme;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FermeMapper {

    FermeDTO toDTO(Ferme ferme);

    Ferme toEntity(FermeDTO fermeDTO);

    List<FermeDTO> toDTOs(List<Ferme> fermes);

    List<Ferme> toEntities(List<FermeDTO> fermeDTOs);

}
