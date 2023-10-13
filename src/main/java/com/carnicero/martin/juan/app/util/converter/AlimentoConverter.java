package com.carnicero.martin.juan.app.util.converter;

import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.model.InformacionNutricionalAlimento;
import com.carnicero.martin.juan.app.request.RegistrarAlimento;

public class AlimentoConverter {


    public static Alimento registrarAlimentoToEntity(RegistrarAlimento data, InformacionNutricionalAlimento informacionData) {
        Alimento alimento = new Alimento();
        alimento.setCantidadAlimento(data.getCantidad());
        alimento.setInformacion(informacionData);
        return alimento;
    }
}
