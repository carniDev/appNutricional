package com.carnicero.martin.juan.app.repository;

import com.carnicero.martin.juan.app.model.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlimentoRepository extends JpaRepository<Alimento,Long> {
}
