package com.carnicero.martin.juan.app.controller;

import com.carnicero.martin.juan.app.exception.CreatedException;
import com.carnicero.martin.juan.app.exception.DeletedException;
import com.carnicero.martin.juan.app.exception.UpdatedException;
import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.model.TipoComida;
import com.carnicero.martin.juan.app.request.EditarComidaRequest;
import com.carnicero.martin.juan.app.request.RegistrarComida;
import com.carnicero.martin.juan.app.response.InformacionComida;
import com.carnicero.martin.juan.app.response.InformacionComidaUsuario;
import com.carnicero.martin.juan.app.service.interfaces.ComidaService;
import com.carnicero.martin.juan.app.service.interfaces.RecomendacionDiariaService;
import com.carnicero.martin.juan.app.util.converter.InformacionComidaConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.carnicero.martin.juan.app.util.Constantes.Constantes.*;
import static com.carnicero.martin.juan.app.util.converter.InformacionComidaConverter.comidaToInformacionUsuario;

@RestController
@RequestMapping(NOMBRE_APP + "/" + COMIDA_CONTROLLER)
public class ComidaController {

    private final ComidaService comidaService;
    private final RecomendacionDiariaService recomendacionDiariaService;

    public ComidaController(ComidaService comidaService, RecomendacionDiariaService recomendacionDiariaService) {
        this.comidaService = comidaService;
        this.recomendacionDiariaService = recomendacionDiariaService;
    }

    @GetMapping(BUSCAR)
    public ResponseEntity listarUnaComidaUsuarioDia(@RequestParam String fecha, @RequestParam String email, @RequestParam TipoComida tipoComida) {
        try {
            InformacionComidaUsuario informacionComida = comidaToInformacionUsuario(comidaService.listarUnaComidaUsuarioFecha(email, fecha, tipoComida));
            return ResponseEntity.ok(informacionComida);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }


    @GetMapping(LISTAR)
    public ResponseEntity listarComidasUsuarioDia(@RequestParam String fecha, @RequestParam String email) {
        List<InformacionComida> informacion = comidaService.listarComidasUsuarioFecha(email, fecha)
                .stream()
                .map(InformacionComidaConverter::comidaToInformacion).
                collect(Collectors.toList());
        return ResponseEntity.ok(informacion.isEmpty() ? SIN_INFORMACION : informacion);
    }

    @PostMapping(REGISTRAR)
    public ResponseEntity registrarComida(@RequestBody RegistrarComida data) {
        try {
            Comida comidaRegistrada = comidaService.registrarComida(data);
            recomendacionDiariaService.actualizar(data.getEmail());
            return ResponseEntity.ok(REGISTRAR_OK);
        } catch (CreatedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(EDITAR)
    public ResponseEntity editarComida(@RequestBody EditarComidaRequest data) {
        try {
            Comida comidaEditada = comidaService.editarComida(data);
            recomendacionDiariaService.actualizar(data.getEmail());
            return ResponseEntity.ok(comidaEditada);
        } catch (UpdatedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping(ELIMINAR)
    public ResponseEntity eliminarComida(@RequestParam final String fechaDia, @RequestParam final String email, @RequestParam TipoComida tipoComida) {
        try {
            comidaService.eliminarComida(fechaDia, email, tipoComida);
            recomendacionDiariaService.actualizar(email);
            return ResponseEntity.ok(ELIMINAR_OK);
        } catch (DeletedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
