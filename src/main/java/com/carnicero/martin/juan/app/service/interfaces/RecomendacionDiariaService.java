package com.carnicero.martin.juan.app.service.interfaces;

import com.carnicero.martin.juan.app.model.RecomendacionDiaria;
import com.carnicero.martin.juan.app.model.Usuario;

public interface RecomendacionDiariaService {

    public RecomendacionDiaria obtenerInformacion(String fechaDia, String email);


}