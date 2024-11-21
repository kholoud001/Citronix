package com.citronix.repository;

import com.citronix.model.entity.DetailRecolte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DetailRecolteRepository extends JpaRepository<DetailRecolte, Long> {
    @Query("SELECT d FROM DetailRecolte d WHERE d.recolte.id = :recolteId")
    List<DetailRecolte> findByRecolteId(@Param("recolteId") Long recolteId);

}
