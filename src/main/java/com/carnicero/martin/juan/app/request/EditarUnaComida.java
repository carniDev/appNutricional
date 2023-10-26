package com.carnicero.martin.juan.app.request;

import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.model.TipoComida;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class EditarUnaComida {

    private String email;
    private String fecha;
    private TipoComida tipoComida;
    private Alimento alimento;
}
