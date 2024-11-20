package com.citronix.mapper;

import com.citronix.dto.RecolteDTO;
import com.citronix.model.entity.Recolte;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DetailRecolteMapper.class})
public interface RecolteMapper {

    @Mapping(source = "champ.id", target = "champId")  // Assurez-vous de mapper le champId correctement
    RecolteDTO toDTO(Recolte recolte);

    @Mapping(source = "champId", target = "champ.id")  // Assurez-vous de mapper champId lors de la conversion en entité
    Recolte toEntity(RecolteDTO recolteDTO);

    List<RecolteDTO> toDTOs(List<Recolte> recoltes);

    List<Recolte> toEntities(List<RecolteDTO> recolteDTOs);
}
