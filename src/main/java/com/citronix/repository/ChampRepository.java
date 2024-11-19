package com.citronix.repository;

import com.citronix.model.entity.Champ;
import com.citronix.model.entity.Ferme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampRepository extends JpaRepository<Champ, Long> {
}