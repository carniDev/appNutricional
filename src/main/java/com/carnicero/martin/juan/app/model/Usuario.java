package com.carnicero.martin.juan.app.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
    private String nombre;
    private String password;
    private String email;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    private Rol rol;
    @OneToMany(mappedBy = "usuario",orphanRemoval = true)
    private List<Comida>comidas;

    public Usuario() {
        this.comidas = new ArrayList<>();
    }
}
