package com.carnicero.martin.juan.app.model;

import com.carnicero.martin.juan.app.request.RegistrarUsuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
    private String nombre;
    private String password;
    @Column(unique = true)
    private String email;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaNacimiento;
    @Column(name = "fecha_registro",updatable = false,nullable = false)
    private LocalDateTime fechaRegistro;
    private Rol rol;
    @Column(name = "inicio_sesion")
    private LocalDateTime inicioSesion;


    public Usuario(RegistrarUsuario usuario) {
        this.nombre= usuario.getNombre();
        this.email = usuario.getEmail();
        this.fechaNacimiento= LocalDate.parse(usuario.getFechaNacimiento(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

}
