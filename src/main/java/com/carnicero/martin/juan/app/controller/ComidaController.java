package com.carnicero.martin.juan.app.controller;

import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.model.InformacionNutricionalAlimento;
import com.carnicero.martin.juan.app.model.TipoComida;
import com.carnicero.martin.juan.app.request.EditarUnaComida;
import com.carnicero.martin.juan.app.request.RegistrarComida;
import com.carnicero.martin.juan.app.response.InformacionComida;
import com.carnicero.martin.juan.app.response.MacroNutritientesComida;
import com.carnicero.martin.juan.app.service.interfaces.ComidaService;
import com.carnicero.martin.juan.app.service.interfaces.RecomendacionDiariaService;
import com.carnicero.martin.juan.app.util.converter.InformacionComidaConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.carnicero.martin.juan.app.util.converter.InformacionComidaConverter.*;

@RestController
@RequestMapping("app-nutricional/comida")
public class ComidaController {

    private final ComidaService comidaService;
    private final RecomendacionDiariaService recomendacionDiariaService;

    public ComidaController(ComidaService comidaService, RecomendacionDiariaService recomendacionDiariaService) {
        this.comidaService = comidaService;
        this.recomendacionDiariaService = recomendacionDiariaService;
    }

    @GetMapping("/listar-una-comida")
    public ResponseEntity listarUnaComidaUsuarioDia(@RequestParam String fecha, @RequestParam String email, @RequestParam TipoComida tipoComida) {
        try {
            InformacionComida informacionComida = comidaToInformacion(comidaService.listarUnaComidaUsuarioFecha(email, fecha, tipoComida));
            return ResponseEntity.ok(informacionComida);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se han encontrado ninguna comida");
        }

    }


    @GetMapping("/listar-comidas")
    public ResponseEntity listarComidasUsuarioDia(@RequestParam String fecha, @RequestParam String email) {
        List<InformacionComida> informacion = comidaService.listarComidasUsuarioFecha(email, fecha)
                .stream()
                .map(InformacionComidaConverter::comidaToInformacion).
                collect(Collectors.toList());
        return ResponseEntity.ok(informacion.isEmpty() ? "No se han encontrado registros" : informacion);
    }

    @PostMapping("registrar")
    public ResponseEntity registrarComida(@RequestBody RegistrarComida data) {

        try {
            Comida comidaRegistrada = comidaService.registrarComida(data);
            return ResponseEntity.ok(comidaRegistrada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("editar")
    public ResponseEntity editarComida(@RequestBody EditarUnaComida data) {
        try {
            Comida comidaEditada = comidaService.editarComida(data);
            return ResponseEntity.ok(comidaEditada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("eliminar")
    public ResponseEntity eliminarComida(@RequestParam final String fechaDia, @RequestParam final String email,@RequestParam TipoComida tipoComida) {
        try {
            comidaService.eliminarComida(fechaDia, email, tipoComida);
            return ResponseEntity.ok("Comida eliminada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido eliminar la comida");
        }
    }



}
