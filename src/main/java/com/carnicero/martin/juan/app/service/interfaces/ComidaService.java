package com.carnicero.martin.juan.app.service.interfaces;

import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.model.TipoComida;
import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.request.EditarComidaRequest;
import com.carnicero.martin.juan.app.request.RegistrarComida;

import java.util.List;

public interface ComidaService {
    List<Comida> listarComidasUsuarioFecha(String email, String fecha);

    Comida listarUnaComidaUsuarioFecha(String email, String fecha, TipoComida tipoComida);

    Comida registrarComida(RegistrarComida data, Usuario usuario);

    Comida editarComida(EditarComidaRequest data,Usuario usuario);

    void eliminarComida(String fechaDia,String email,TipoComida tipoComida);
}
