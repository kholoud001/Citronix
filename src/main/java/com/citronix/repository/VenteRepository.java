package com.citronix.repository;

import com.citronix.model.entity.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenteRepository extends JpaRepository<Vente, Long> {
    List<Vente> findByRecolte_Id(Long recolteId);
}
