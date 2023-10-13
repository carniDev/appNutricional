package com.carnicero.martin.juan.app.request;

import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.model.TipoComida;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RegistrarComida {

    private TipoComida tipoComida;
    private List<Alimento>listadoAlimentos;
    private String email;


    public RegistrarComida() {
        this.listadoAlimentos = new ArrayList<>();
    }
}
