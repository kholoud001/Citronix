package com.citronix.mapper;

import com.citronix.dto.VenteDTO;
import com.citronix.model.entity.Vente;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = RecolteMapper.class)
public interface VenteMapper {
    VenteDTO toDTO(Vente vente);

    Vente toEntity(VenteDTO venteDTO);

    List<VenteDTO> toDTOs(List<Vente> ventes);

    List<Vente> toEntities(List<VenteDTO> venteDTOs);
}
