package com.carnicero.martin.juan.app.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Email(message = "El campo email no puede estar vacio")
    String email;
    @NotEmpty(message = "El campo password no puede estar vacio")
    String password; 
}
