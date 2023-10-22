package com.carnicero.martin.juan.app.repository;

import com.carnicero.martin.juan.app.model.RecomendacionDiaria;
import com.carnicero.martin.juan.app.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RecomendacionDiariaRepository extends JpaRepository<RecomendacionDiaria,Long> {

    Optional<RecomendacionDiaria>findByFechaAndUsuario(LocalDate fecha, Usuario usuario);
}
