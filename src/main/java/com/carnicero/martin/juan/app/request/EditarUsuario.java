package com.carnicero.martin.juan.app.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class EditarUsuario {
    @NotEmpty(message = "El campo nombre no puede estar vacio")
    private String nombre;
    @NotEmpty(message = "El campo password no puede estar vacio")
    private String password;
    @Email
    private String email;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaNacimiento;

}
