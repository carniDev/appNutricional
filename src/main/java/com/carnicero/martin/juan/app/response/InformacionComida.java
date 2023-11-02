package com.carnicero.martin.juan.app.response;

import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.model.TipoComida;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class InformacionComida {
    private TipoComida tipoComida;
    private LocalDate fechaComida;
    private String nombreUsuario;
    private String email;
    private int kcal;
    private int hidratosCarbono;
    private int proteinas;
    private int grasas;
    private List<Alimento> alimentos;
}
