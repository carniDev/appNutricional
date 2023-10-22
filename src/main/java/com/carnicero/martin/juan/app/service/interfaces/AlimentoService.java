package com.carnicero.martin.juan.app.service.interfaces;

import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.request.EditarAlimento;
import com.carnicero.martin.juan.app.request.RegistrarAlimento;

public interface AlimentoService {

    Alimento buscarAlimento(Long id);
    Alimento registrarAlimento(RegistrarAlimento data);

    Alimento editarAlimento(Alimento original,Alimento data);

}
