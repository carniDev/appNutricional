package com.carnicero.martin.juan.app.controller;

import com.carnicero.martin.juan.app.exception.CreatedException;
import com.carnicero.martin.juan.app.exception.DeletedException;
import com.carnicero.martin.juan.app.exception.UpdatedException;
import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.request.EditarAlimento;
import com.carnicero.martin.juan.app.request.RegistrarAlimento;
import com.carnicero.martin.juan.app.service.interfaces.AlimentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static com.carnicero.martin.juan.app.util.Constantes.Constantes.*;

@RestController
@RequestMapping(ALIMENTO_CONTROLLER+"/"+ALIMENTO_CONTROLLER)
public class AlimentoController {

    private final AlimentoService alimentoService;

    public AlimentoController(AlimentoService alimentoService) {
        this.alimentoService = alimentoService;
    }

    @GetMapping(LISTAR)
    public ResponseEntity listarAlimentos() {
        List<Alimento> alimentos = alimentoService.listarAlimentos();
        return ResponseEntity.ok(alimentos.isEmpty() ? Collections.emptyList() : alimentos);
    }

    @PostMapping(REGISTRAR)
    public ResponseEntity registrarAlimento(@RequestBody RegistrarAlimento data) {

        try {
            Alimento alimentoParaRegistrar = alimentoService.registrarAlimento(data);
            return ResponseEntity.ok(REGISTRAR_OK);
        } catch (CreatedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ERROR_REGISTRAR);
        }
    }

    @PutMapping(EDITAR)
    public ResponseEntity editarAlimento(@RequestParam Long id, @RequestBody EditarAlimento editarAlimento) {
        try {
            Alimento actualizado = alimentoService.editarAlimento(id, editarAlimento);
            return ResponseEntity.ok(EDITAR_OK);
        } catch (UpdatedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ERROR_EDITAR);
        }
    }

    @DeleteMapping(ELIMINAR)
    public ResponseEntity eliminarAlimento(@RequestParam Long id){
        try{
            alimentoService.eliminarAlimento(id);
            return ResponseEntity.ok(ELIMINAR_OK);

        }catch (DeletedException e){
            throw  new DeletedException("No se ha podido eliminar");
        }
    }

}
