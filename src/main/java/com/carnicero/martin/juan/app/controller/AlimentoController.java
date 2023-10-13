package com.carnicero.martin.juan.app.controller;

import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.request.RegistrarAlimento;
import com.carnicero.martin.juan.app.service.AlimentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("app-nutricional/alimento")
public class AlimentoController {

    private final AlimentoService alimentoService;

    public AlimentoController(AlimentoService alimentoService) {
        this.alimentoService = alimentoService;
    }

    public ResponseEntity listarAlimentos(@RequestParam String email) {

        return null;
    }

    public ResponseEntity registrarAlimento(@RequestParam RegistrarAlimento data) {

        try {
            Alimento alimentoParaRegistrar = alimentoService.registrarAlimento(data);
            return ResponseEntity.ok(alimentoParaRegistrar);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido registrar el alimento");

        }


    }
}
