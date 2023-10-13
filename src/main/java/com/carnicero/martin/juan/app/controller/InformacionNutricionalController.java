package com.carnicero.martin.juan.app.controller;

import com.carnicero.martin.juan.app.service.InformacionNutricionalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app-nutricional/informacion-nutricional")
public class InformacionNutricionalController {
private final InformacionNutricionalService informacionService;

    public InformacionNutricionalController(InformacionNutricionalService informacionService) {
        this.informacionService = informacionService;
    }

    @GetMapping("/listado")
    public ResponseEntity listarAlimentos(){
        List<?> informacionAlimentos = informacionService.obtenerInformacion();
        return ResponseEntity.ok(informacionAlimentos.isEmpty()?"No hay alimentos para mostrar":informacionAlimentos);
    }


}
