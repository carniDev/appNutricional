package com.carnicero.martin.juan.app.service;

import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.request.RegistrarAlimento;

public interface AlimentoService {
    Alimento registrarAlimento(RegistrarAlimento data);
}
