package com.carnicero.martin.juan.app.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditarAlimento {

    private Long idAlimento;
    private int cantidad;
    private String codigoAlimento;


}
