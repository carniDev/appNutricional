package com.carnicero.martin.juan.app.controller;

import com.carnicero.martin.juan.app.service.RecomendacionDiariaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("app-nutricional/dashboard")
public class RecomendacionDiariaController {


private final RecomendacionDiariaService recomendacionService;

    public RecomendacionDiariaController(RecomendacionDiariaService recomendacionService) {
        this.recomendacionService = recomendacionService;
    }

    @GetMapping("informacion-diaria")
    public ResponseEntity obtenerInformacionDiaria(@RequestParam String fechaDia, @RequestParam String email){
        try{
            return ResponseEntity.ok(recomendacionService.obtenerInformacion(fechaDia,email));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
