package com.carnicero.martin.juan.app.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotEmpty(message = "El campo nombre no puede estar vacio")
    String nombre;
    @Email(message = "Debe contener un email valido")
    String email;
    @NotEmpty(message = "El campo password no puede estar vacio")
    String password;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotEmpty(message = "El campo fecha de nacimiento no puede estar vacio")
    String fechaNacimiento;
}
