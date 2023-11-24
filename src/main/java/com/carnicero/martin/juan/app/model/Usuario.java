package com.carnicero.martin.juan.app.model;

import com.carnicero.martin.juan.app.request.RegistrarUsuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((rol.name())));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
