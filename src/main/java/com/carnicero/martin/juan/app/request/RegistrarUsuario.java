package com.carnicero.martin.juan.app.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrarUsuario {
    @NotEmpty(message = "El campo nombre no puede estar vacio")
    private String nombre;
    @Email
    private String email;
    @NotEmpty(message = "La fecha de nacimiento no puede estar vacia")
    private String fechaNacimiento;
}
