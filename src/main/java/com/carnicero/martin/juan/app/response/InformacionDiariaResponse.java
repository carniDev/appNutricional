package com.carnicero.martin.juan.app.response;

import com.carnicero.martin.juan.app.model.Comida;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InformacionDiariaResponse {
    private double kcal;
    private double hidratosCarbono;
    private double proteinas;
    private double grasas;
    private List<Comida> comidas;
}
