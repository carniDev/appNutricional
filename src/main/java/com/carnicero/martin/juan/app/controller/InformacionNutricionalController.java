package com.carnicero.martin.juan.app.controller;
import com.carnicero.martin.juan.app.exception.CreatedException;
import com.carnicero.martin.juan.app.exception.DeletedException;
import com.carnicero.martin.juan.app.exception.UpdatedException;
import com.carnicero.martin.juan.app.request.EditarInformacionNutricional;
import com.carnicero.martin.juan.app.request.InformacionNutricional;
import com.carnicero.martin.juan.app.service.interfaces.InformacionNutricionalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.carnicero.martin.juan.app.util.Constantes.Constantes.*;

@RestController
@RequestMapping(NOMBRE_APP+"/"+INFORMACION_NUTRICIONAL)
@RequiredArgsConstructor
public class InformacionNutricionalController {
private final InformacionNutricionalService informacionService;


    @GetMapping(LISTAR)
    public ResponseEntity listarAlimentos(){
        List<?> informacionAlimentos = informacionService.obtenerInformacion();
        return ResponseEntity.ok(informacionAlimentos.isEmpty()? SIN_INFORMACION :informacionAlimentos);
    }
    @GetMapping("listar-nombre")
    public ResponseEntity listarAlimentosByNombre(@RequestParam String nombre){
        List<?> informacionAlimentos = informacionService.obtenerInformacionByNombre(nombre);
        return ResponseEntity.ok(informacionAlimentos.isEmpty()? SIN_INFORMACION :informacionAlimentos);
    }

    @PostMapping(REGISTRAR)
    public ResponseEntity registrarAlimentos(@Valid @RequestBody InformacionNutricional data){
        try {
            return ResponseEntity.ok(informacionService.registrarAlimento(data));
        }catch (CreatedException e){
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(EDITAR)
    public ResponseEntity editarInformacionAlimento(@RequestParam String codigo,@Valid @RequestBody EditarInformacionNutricional data){

        try {
            return ResponseEntity.ok(informacionService.editarAlimento(codigo,data));
        }catch (UpdatedException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping(ELIMINAR)
    public ResponseEntity eliminarInformacionAlimento(@RequestParam String codigo){
        try{
            informacionService.eliminarAlimento(codigo);
            return ResponseEntity.ok(ELIMINAR_OK);
        }catch (DeletedException e){
           return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
