package com.carnicero.martin.juan.app.util.converter;

import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.request.RegistrarComida;

import java.time.LocalDate;

public class ComidaConverter {

    public static Comida registrarComidaToEntity(RegistrarComida data, Usuario usuario){
        Comida comidaParaRegistrar = new Comida();
        comidaParaRegistrar.setFechaComida(LocalDateConverter.stringToLocalDateConverter(data.getFechaComida()));
        comidaParaRegistrar.setTipoComida(data.getTipoComida());
        comidaParaRegistrar.setListadoAlimentos(data.getListadoAlimentos());
        comidaParaRegistrar.setUsuario(usuario);
        return comidaParaRegistrar;
    }
}
