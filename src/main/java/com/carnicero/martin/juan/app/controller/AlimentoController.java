package com.carnicero.martin.juan.app.controller;

import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.request.EditarAlimento;
import com.carnicero.martin.juan.app.request.RegistrarAlimento;
import com.carnicero.martin.juan.app.service.interfaces.AlimentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("app-nutricional/alimento")
public class AlimentoController {

    private final AlimentoService alimentoService;

    public AlimentoController(AlimentoService alimentoService) {
        this.alimentoService = alimentoService;
    }

    @GetMapping("/listar")
    public ResponseEntity listarAlimentos() {
        List<Alimento> alimentos = alimentoService.listarAlimentos();

        return ResponseEntity.ok(alimentos.isEmpty() ? Collections.emptyList() : alimentos);
    }

    @PostMapping("/registrar")
    public ResponseEntity registrarAlimento(@RequestBody RegistrarAlimento data) {

        try {
            Alimento alimentoParaRegistrar = alimentoService.registrarAlimento(data);
            return ResponseEntity.ok(alimentoParaRegistrar);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido registrar el alimento");
        }
    }

    @PutMapping("/editar")
    public ResponseEntity editarAlimento(@RequestParam Long id,@RequestBody EditarAlimento editarAlimento){
        try{
            Alimento actualizado = alimentoService.editarAlimento(id,editarAlimento);
            return ResponseEntity.ok("Alimento editado correctamente");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido editar el alimento");

        }
    }
}
