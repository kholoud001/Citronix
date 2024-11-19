package com.citronix.mapper;

import com.citronix.dto.RecolteDTO;
import com.citronix.model.entity.Recolte;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecolteMapper {
    RecolteDTO toDTO(Recolte recolte);

    Recolte toEntity(RecolteDTO recolteDTO);

    List<RecolteDTO> toDTOs(List<Recolte> recoltes);

    List<Recolte> toEntities(List<RecolteDTO> recolteDTOs);
}
