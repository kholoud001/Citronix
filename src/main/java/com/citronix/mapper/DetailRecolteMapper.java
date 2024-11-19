package com.citronix.mapper;

import com.citronix.dto.DetailRecolteDTO;
import com.citronix.model.entity.DetailRecolte;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ArbreMapper.class, RecolteMapper.class})
public interface DetailRecolteMapper {
    DetailRecolteDTO toDTO(DetailRecolte detailRecolte);

    DetailRecolte toEntity(DetailRecolteDTO detailRecolteDTO);

    List<DetailRecolteDTO> toDTOs(List<DetailRecolte> detailRecoltes);

    List<DetailRecolte> toEntities(List<DetailRecolteDTO> detailRecolteDTOs);
}
