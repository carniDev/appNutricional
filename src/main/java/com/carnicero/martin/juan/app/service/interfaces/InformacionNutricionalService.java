package com.carnicero.martin.juan.app.service.interfaces;

import com.carnicero.martin.juan.app.model.InformacionNutricionalAlimento;
import com.carnicero.martin.juan.app.request.EditarInformacionNutricional;
import com.carnicero.martin.juan.app.request.InformacionNutricional;

import java.util.List;

public interface InformacionNutricionalService {

    InformacionNutricionalAlimento obtenerInformacion(String codigoAlimento);
    List<InformacionNutricionalAlimento>obtenerInformacion();
    List<?> obtenerInformacionByNombre(String nombre);
    InformacionNutricionalAlimento registrarAlimento(InformacionNutricional data);
    InformacionNutricionalAlimento editarAlimento(String codigo,EditarInformacionNutricional data);

    void eliminarAlimento(String codigo);


}
