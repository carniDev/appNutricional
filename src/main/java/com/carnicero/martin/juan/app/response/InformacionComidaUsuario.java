package com.carnicero.martin.juan.app.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InformacionComidaUsuario {
    private long idComida;
    private String tipoComida;
    private double cantidad;
    private String codigoAlimento;
    private String nombreAlimento;


}
