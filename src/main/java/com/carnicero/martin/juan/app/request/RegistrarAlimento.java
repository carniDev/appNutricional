package com.carnicero.martin.juan.app.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrarAlimento {
        @Min(value = 1)
        private int cantidad;
        @NotEmpty(message = "El campo codigo no puede estar vacio")
        private String codigoAlimento;

}
