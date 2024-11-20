package com.citronix.repository;

import com.citronix.model.entity.Champ;
import com.citronix.model.entity.Ferme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChampRepository extends JpaRepository<Champ, Long> {


}