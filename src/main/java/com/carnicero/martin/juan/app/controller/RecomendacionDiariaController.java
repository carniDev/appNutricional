package com.carnicero.martin.juan.app.controller;

import com.carnicero.martin.juan.app.service.interfaces.RecomendacionDiariaService;
import com.carnicero.martin.juan.app.util.Constantes.Constantes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.carnicero.martin.juan.app.util.Constantes.Constantes.*;

@RestController
@RequestMapping(NOMBRE_APP +"/"+RECOMENDACION_CONTROLLER)
public class RecomendacionDiariaController {


private final RecomendacionDiariaService recomendacionService;

    public RecomendacionDiariaController(RecomendacionDiariaService recomendacionService) {
        this.recomendacionService = recomendacionService;
    }

    @GetMapping(INFORMACION_DIARIA)
    public ResponseEntity obtenerInformacionDiaria(@RequestParam String fechaDia, @RequestParam String email){
        try{
            return ResponseEntity.ok(recomendacionService.obtenerInformacion(fechaDia,email));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping(REGISTRAR)
    public ResponseEntity registrarInformacionDiaria(@RequestParam String email){
        try{
            return ResponseEntity.ok(recomendacionService.crearRecomendacionDiaria(email));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
