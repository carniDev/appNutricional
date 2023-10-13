package com.carnicero.martin.juan.app.repository;

import com.carnicero.martin.juan.app.model.Comida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ComidaRepository extends JpaRepository<Comida,Long> {

    List<Comida> findAllByFechaComidaAndUsuarioEmail(LocalDate fecha, String email);
}
