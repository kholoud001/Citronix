package com.citronix.mapper;

import com.citronix.dto.ChampDTO;
import com.citronix.model.entity.Champ;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = FermeMapper.class)
public interface ChampMapper {
    ChampDTO toDTO(Champ champ);

    Champ toEntity(ChampDTO champDTO);

    List<ChampDTO> toDTOs(List<Champ> champs);

    List<Champ> toEntities(List<ChampDTO> champDTOs);
}
