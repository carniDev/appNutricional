package com.carnicero.martin.juan.app.request;

import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.model.TipoComida;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EditarUnaComida {

    private String email;
    private String fecha;
    private TipoComida tipoComida;
    List<Alimento> alimentos;
}
