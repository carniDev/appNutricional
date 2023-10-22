package com.carnicero.martin.juan.app.service.interfaces;

import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.model.RecomendacionDiaria;
import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.response.MacroNutritientesComida;

import java.time.LocalDate;

public interface RecomendacionDiariaService {

    public RecomendacionDiaria obtenerInformacion(String fechaDia, String email);
    RecomendacionDiaria actualizarPositivo(Comida comida);
    RecomendacionDiaria actualizarNegativo(Comida comida);



}
