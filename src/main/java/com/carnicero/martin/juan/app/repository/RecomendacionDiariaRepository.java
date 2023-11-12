package com.carnicero.martin.juan.app.repository;

import com.carnicero.martin.juan.app.model.RecomendacionDiaria;
import com.carnicero.martin.juan.app.model.Usuario;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RecomendacionDiariaRepository extends JpaRepository<RecomendacionDiaria,Long> {

    RecomendacionDiaria findByFechaAndUsuarioEmail(LocalDate fecha, String email);
}
