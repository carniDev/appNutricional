package com.carnicero.martin.juan.app.util.converter;

import com.carnicero.martin.juan.app.model.InformacionNutricionalAlimento;
import com.carnicero.martin.juan.app.request.EditarInformacionNutricional;
import com.carnicero.martin.juan.app.request.InformacionNutricional;

public class InformacionNutricionalConverter {

    public static InformacionNutricionalAlimento infomacionNutricionalToEntity(InformacionNutricional data){
        InformacionNutricionalAlimento alimento = new InformacionNutricionalAlimento();
        alimento.setNombre(data.getNombre());
        alimento.setKcal(data.getKcal());
        alimento.setHidratosCarbono(data.getHidratosCarbono());
        alimento.setProteinas(data.getProteinas());
        alimento.setGrasas(data.getGrasas());
        return alimento;
    }

    public static void editarInfomacionNutricionalToEntity(InformacionNutricionalAlimento alimento,EditarInformacionNutricional data){
        alimento.setNombre(data.getNombre());
        alimento.setKcal(data.getKcal());
        alimento.setHidratosCarbono(data.getHidratosCarbono());
        alimento.setProteinas(data.getProteinas());
        alimento.setGrasas(data.getGrasas());

    }
}
