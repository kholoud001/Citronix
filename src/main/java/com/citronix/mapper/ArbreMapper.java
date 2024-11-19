package com.citronix.mapper;

import com.citronix.dto.ArbreDTO;
import com.citronix.model.entity.Arbre;
import org.mapstruct.*;

import java.util.List;


@Mapper(componentModel = "spring", uses = ChampMapper.class)
public interface ArbreMapper {
    ArbreDTO toDTO(Arbre arbre);

    Arbre toEntity(ArbreDTO arbreDTO);

    List<ArbreDTO> toDTOs(List<Arbre> arbres);

    List<Arbre> toEntities(List<ArbreDTO> arbreDTOs);
}
