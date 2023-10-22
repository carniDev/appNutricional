package com.carnicero.martin.juan.app.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditarInformacionNutricional {
    @NotEmpty(message = "El campo nombre no puede estar vacio")
    private String nombre;
    @Min(value = 0)
    private int kcal;
    @Min(value = 0)
    private int hidratosCarbono;
    @Min(value = 0)
    private int proteinas;
    @Min(value = 0)
    private int grasas;
}
