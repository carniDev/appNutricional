package com.carnicero.martin.juan.app.service.interfaces;

import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.request.EditarAlimento;
import com.carnicero.martin.juan.app.request.RegistrarAlimento;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AlimentoService {

    Alimento buscarAlimento(Long id);
    Alimento registrarAlimento(RegistrarAlimento data);
    Alimento editarAlimento(Long id,EditarAlimento data);
    void eliminarAlimentos(List<Alimento> alimentos);

    List<Alimento> listarAlimentos();
}
