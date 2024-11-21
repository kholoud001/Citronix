package com.citronix.repository;

import com.citronix.model.entity.Champ;
import com.citronix.model.entity.Ferme;
import com.citronix.model.entity.Recolte;
import com.citronix.model.enums.Saison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecolteRepository extends JpaRepository<Recolte, Long> {


    boolean existsByChampAndSaison(Champ champ, Saison saison);
    List<Recolte> findBySaison(Saison saison);


}
