package com.carnicero.martin.juan.app.service;

import com.carnicero.martin.juan.app.model.InformacionNutricionalAlimento;
import com.carnicero.martin.juan.app.request.EditarInformacionNutricional;
import com.carnicero.martin.juan.app.request.InformacionNutricional;

import java.util.List;

public interface InformacionNutricionalService {

    public InformacionNutricionalAlimento obtenerInformacion(String codigoAlimento);
    public List<InformacionNutricionalAlimento>obtenerInformacion();
    public InformacionNutricionalAlimento registrarAlimento(InformacionNutricional data);
    public InformacionNutricionalAlimento editarAlimento(String codigo,EditarInformacionNutricional data);

    void eliminarAlimento(String codigo);
}
