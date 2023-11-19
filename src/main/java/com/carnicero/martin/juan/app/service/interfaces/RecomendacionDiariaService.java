package com.carnicero.martin.juan.app.service.interfaces;

import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.model.RecomendacionDiaria;
import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.request.EditarComidaRequest;
import com.carnicero.martin.juan.app.request.RegistrarComida;
import com.carnicero.martin.juan.app.response.InformacionDiariaResponse;
import com.carnicero.martin.juan.app.response.MacroNutritientesComida;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public interface RecomendacionDiariaService {

    public InformacionDiariaResponse obtenerInformacion(String fechaDia, String email);
    RecomendacionDiaria crearRecomendacionDiaria(String email);

    void actualizar(String email);
    void actualizar(String email, String fecha);
}
