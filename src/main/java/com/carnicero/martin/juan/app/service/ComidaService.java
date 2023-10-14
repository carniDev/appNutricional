package com.carnicero.martin.juan.app.service;

import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.request.EditarUnaComida;
import com.carnicero.martin.juan.app.request.RegistrarComida;

import java.util.List;

public interface ComidaService {
    List<?> listarComidasUsuarioFecha(String email, String fecha);


    Comida registrarComida(RegistrarComida data);

    Comida editarComida(EditarUnaComida data);
}
