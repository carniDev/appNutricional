package com.carnicero.martin.juan.app.repository;

import com.carnicero.martin.juan.app.model.RecomendacionDiaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecomendacionDiariaRepository extends JpaRepository<RecomendacionDiaria,Long> {
}
