package com.carnicero.martin.juan.app.request;

import com.carnicero.martin.juan.app.model.TipoComida;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrarComida {

    private TipoComida tipoComida;
    private int cantidadComida;
    private String codigoAlimento;
    private String email;

}
