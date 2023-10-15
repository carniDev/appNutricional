package com.carnicero.martin.juan.app.service;

import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.model.TipoComida;
import com.carnicero.martin.juan.app.request.EditarUnaComida;
import com.carnicero.martin.juan.app.request.RegistrarComida;

import java.util.List;

public interface ComidaService {
    List<Comida> listarComidasUsuarioFecha(String email, String fecha);

    public Comida listarUnaComidaUsuarioFecha(String email, String fecha, TipoComida tipoComida);

    Comida registrarComida(RegistrarComida data);

    Comida editarComida(EditarUnaComida data);
}
