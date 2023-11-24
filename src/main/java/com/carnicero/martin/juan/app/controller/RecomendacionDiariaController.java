package com.carnicero.martin.juan.app.controller;

import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.service.interfaces.RecomendacionDiariaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.carnicero.martin.juan.app.util.Constantes.Constantes.*;

@RestController
@RequestMapping(NOMBRE_APP +"/"+RECOMENDACION_CONTROLLER)
public class RecomendacionDiariaController {


private final RecomendacionDiariaService recomendacionService;

    public RecomendacionDiariaController(RecomendacionDiariaService recomendacionService) {
        this.recomendacionService = recomendacionService;
    }

    @GetMapping(INFORMACION_DIARIA)
    public ResponseEntity obtenerInformacionDiaria(@RequestParam String fechaDia){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication.isAuthenticated()&& authentication!=null){
                Usuario usuario = (Usuario) authentication.getPrincipal();
                return ResponseEntity.ok(recomendacionService.obtenerInformacion(fechaDia,usuario));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }

    }



}
