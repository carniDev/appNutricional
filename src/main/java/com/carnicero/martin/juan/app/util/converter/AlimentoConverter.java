package com.carnicero.martin.juan.app.util.converter;

import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.model.InformacionNutricionalAlimento;
import com.carnicero.martin.juan.app.request.EditarAlimento;
import com.carnicero.martin.juan.app.request.RegistrarAlimento;

public class AlimentoConverter {


    public static Alimento registrarAlimentoToEntity(RegistrarAlimento data, InformacionNutricionalAlimento informacionData) {
        Alimento alimento = new Alimento();
        alimento.setCantidadAlimento(data.getCantidad());
        alimento.setInformacion(informacionData);
        return alimento;
    }

    public static Alimento registrarAlimentoToEntityComida(int cantidad, InformacionNutricionalAlimento informacionData) {
        Alimento alimento = new Alimento();
        alimento.setCantidadAlimento(cantidad);
        alimento.setInformacion(informacionData);
        return alimento;
    }
    public static void editarAlimentoToEntity(EditarAlimento data, Alimento alimento,InformacionNutricionalAlimento informacion) {
        alimento.setCantidadAlimento(data.getCantidad());
        alimento.setInformacion(informacion);
    }

    public static void alimentoEditadoToEntity(Alimento original, Alimento editado){
        original.setCantidadAlimento(editado.getCantidadAlimento());
        original.setInformacion(editado.getInformacion());
    }
}
