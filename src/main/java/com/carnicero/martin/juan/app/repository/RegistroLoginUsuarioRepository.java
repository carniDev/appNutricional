package com.carnicero.martin.juan.app.repository;

import com.carnicero.martin.juan.app.model.RegistroLoginUsuario;
import com.carnicero.martin.juan.app.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RegistroLoginUsuarioRepository extends JpaRepository<RegistroLoginUsuario, Long> {
    Optional<RegistroLoginUsuario> findByUsuario(Usuario usuario);
}
