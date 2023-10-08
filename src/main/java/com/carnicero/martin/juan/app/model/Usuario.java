package com.carnicero.martin.juan.app.model;

import jakarta.persistence.*;

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
    private String foto;
    private Rol rol;
    @OneToMany(mappedBy = "usuario",orphanRemoval = true)
    private List<Comida>comidas;

}
