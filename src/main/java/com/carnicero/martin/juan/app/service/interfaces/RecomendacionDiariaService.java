package com.carnicero.martin.juan.app.service.interfaces;

import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.response.InformacionDiariaResponse;

public interface RecomendacionDiariaService {

    public InformacionDiariaResponse obtenerInformacion(String fechaDia, Usuario usuario);

    void actualizar(String email);
    void actualizar(String email, String fecha);
}
