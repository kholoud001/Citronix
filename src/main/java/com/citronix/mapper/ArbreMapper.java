package com.citronix.mapper;

import com.citronix.dto.ArbreDTO;
import com.citronix.model.entity.Arbre;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ChampMapper.class, DetailRecolteMapper.class})
public interface ArbreMapper {

    @Mapping(source = "champ.id", target = "champId")
    //@Mapping(target = "details", ignore = true)
    ArbreDTO toDTO(Arbre arbre);

    @Mapping(target = "age", ignore = true)
    @Mapping(source = "champId", target = "champ.id")
    //@Mapping(target = "details", ignore = true)
    Arbre toEntity(ArbreDTO arbreDTO);

    List<ArbreDTO> toDTOs(List<Arbre> arbres);

    List<Arbre> toEntities(List<ArbreDTO> arbreDTOs);
}
