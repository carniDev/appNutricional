package com.carnicero.martin.juan.app.util.converter;

import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.response.InformacionComida;

public class InformacionComidaConverter {
    public static InformacionComida comidaToInformacion(Comida data){
        InformacionComida informacionComida = new InformacionComida();
        informacionComida.setNombreUsuario(data.getUsuario().getNombre());
        informacionComida.setEmail(data.getUsuario().getEmail());
        informacionComida.setTipoComida(data.getTipoComida());
        informacionComida.setFechaComida(data.getFechaComida());
        informacionComida.setAlimentos(data.getListadoAlimentos());
        return informacionComida;

    }
}
