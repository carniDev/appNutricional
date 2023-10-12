package com.carnicero.martin.juan.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "registro_login_usuarios")
@Getter
@Setter
public class RegistroLoginUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @Column(name = "inicio_sesion")
    private LocalDateTime inicioSesion;


}
