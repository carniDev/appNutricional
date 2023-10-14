package com.carnicero.martin.juan.app.controller;

import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.request.EditarUnaComida;
import com.carnicero.martin.juan.app.request.RegistrarComida;
import com.carnicero.martin.juan.app.service.ComidaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("app-nutricional/comida")
public class ComidaController {

    private final ComidaService comidaService;

    public ComidaController(ComidaService comidaService) {
        this.comidaService = comidaService;
    }

    @GetMapping("listar-comidas/")
    public ResponseEntity listarComidasUsuarioDia(@RequestParam String fecha, @RequestParam String email) {
        List<?> listarComidas = comidaService.listarComidasUsuarioFecha(email, fecha);

        return ResponseEntity.ok(listarComidas.isEmpty() ? "No se han encontrado registros" : listarComidas);
    }

    @PostMapping("registrar")
    public ResponseEntity registrarComida(@RequestBody RegistrarComida data) {

        try {
            Comida comidaRegistrada = comidaService.registrarComida(data);
            return ResponseEntity.ok(comidaRegistrada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha registrado correctamente la comida");
        }
    }

    @PutMapping("editar")
    public ResponseEntity editarComida(@RequestBody EditarUnaComida data) {
        try {
            Comida comidaEditada = comidaService.editarComida(data);
            return ResponseEntity.ok(comidaEditada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha registrado correctamente la comida");
        }
    }

}
