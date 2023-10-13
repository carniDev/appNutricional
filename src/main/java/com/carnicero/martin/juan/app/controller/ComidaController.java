package com.carnicero.martin.juan.app.controller;

import com.carnicero.martin.juan.app.request.RegistrarComida;
import com.carnicero.martin.juan.app.service.ComidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        return null;
    }

}
