package com.citronix.service;

import com.citronix.dto.ChampDTO;
import com.citronix.model.entity.Champ;

public interface ChampService {
    ChampDTO createChamp(Long fermeId, ChampDTO champDTO);

    ChampDTO getChamp(Long champId);
}
