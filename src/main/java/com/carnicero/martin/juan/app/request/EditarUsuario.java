package com.carnicero.martin.juan.app.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditarUsuario {
    @NotEmpty(message = "El campo nombre no puede estar vacio")
    private String nombre;

}
