package com.carnicero.martin.juan.app.controller;

import com.carnicero.martin.juan.app.request.EditarInformacionNutricional;
import com.carnicero.martin.juan.app.request.InformacionNutricional;
import com.carnicero.martin.juan.app.service.interfaces.InformacionNutricionalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/registrar")
    public ResponseEntity registrarAlimentos(@Valid @RequestBody InformacionNutricional data){
        try {
            return ResponseEntity.ok(informacionService.registrarAlimento(data));
        }catch (RuntimeException e){
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido registrar el alimento");
        }
    }

    @PutMapping("/editar")
    public ResponseEntity editarInformacionAlimento(@RequestParam String codigo,@Valid @RequestBody EditarInformacionNutricional data){

        try {
            return ResponseEntity.ok(informacionService.editarAlimento(codigo,data));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido editar el alimento");
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity eliminarInformacionAlimento(@RequestParam String codigo){
        try{
            informacionService.eliminarAlimento(codigo);
            return ResponseEntity.ok("Alimento eliminado");
        }catch (RuntimeException e){
           return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido eliminar el alimento");
        }
    }

}
