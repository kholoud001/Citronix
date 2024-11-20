package com.citronix.mapper;

import com.citronix.dto.ArbreDTO;
import com.citronix.model.entity.Arbre;
import org.mapstruct.*;

import java.util.List;


@Mapper(componentModel = "spring", uses = ChampMapper.class)
public interface ArbreMapper {

    @Mapping(source = "champ.id", target = "champId")
    ArbreDTO toDTO(Arbre arbre);

    @Mapping(target = "age", ignore = true) // L'âge est calculé, pas entré par l'utilisateur
    @Mapping(source = "champId", target = "champ.id")
    Arbre toEntity(ArbreDTO arbreDTO);

    List<ArbreDTO> toDTOs(List<Arbre> arbres);

    List<Arbre> toEntities(List<ArbreDTO> arbreDTOs);
}
