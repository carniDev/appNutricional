package com.carnicero.martin.juan.app.util.converter;

import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.request.RegistrarComida;

import java.time.LocalDate;

public class ComidaConverter {

    public static Comida registrarComidaToEntity(RegistrarComida data, Alimento alimento, Usuario usuario){
        Comida comidaParaRegistrar = new Comida();
        comidaParaRegistrar.setFechaComida(data.getFecha());
        comidaParaRegistrar.setTipoComida(data.getTipoComida());
        comidaParaRegistrar.setUsuario(usuario);
        comidaParaRegistrar.getListadoAlimentos().add(alimento);
        return comidaParaRegistrar;
    }
}
