package com.citronix.service.impl;

import com.citronix.model.entity.Champ;
import com.citronix.repository.ChampRepository;
import com.citronix.service.ChampService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChampServiceImpl implements ChampService {

    @Autowired
    private ChampRepository champRepository;

    @Override
    public Champ saveChamp(Champ champ) {
        return champRepository.save(champ);
    }

}
