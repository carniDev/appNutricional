package com.carnicero.martin.juan.app.repository;

import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.model.TipoComida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ComidaRepository extends JpaRepository<Comida,Long> {

    List<Comida> findAllByFechaComidaAndUsuarioEmail(LocalDate fecha, String email);

    Optional<Comida> findByFechaComidaAndUsuarioEmailAndTipoComida(LocalDate fecha, String email, TipoComida tipoComida);
    Boolean existsByFechaComidaAndUsuarioEmailAndTipoComida(LocalDate fecha, String email, TipoComida tipoComida);

}
