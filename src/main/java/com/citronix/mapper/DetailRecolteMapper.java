package com.citronix.mapper;

import com.citronix.dto.DetailRecolteDTO;
import com.citronix.model.entity.DetailRecolte;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ArbreMapper.class, RecolteMapper.class})
public interface DetailRecolteMapper {

    @Mapping(source = "arbre.id", target = "arbreId")  // Mappage correct de arbreId
    @Mapping(source = "recolte.id", target = "recolteId")  // Mappage correct de recolteId
    DetailRecolteDTO toDTO(DetailRecolte detailRecolte);

    @Mapping(source = "arbreId", target = "arbre.id")  // Mappage inverse pour arbre
    @Mapping(source = "recolteId", target = "recolte.id")  // Mappage inverse pour recolte
    DetailRecolte toEntity(DetailRecolteDTO detailRecolteDTO);

    List<DetailRecolteDTO> toDTOs(List<DetailRecolte> detailRecoltes);

    List<DetailRecolte> toEntities(List<DetailRecolteDTO> detailRecolteDTOs);
}
