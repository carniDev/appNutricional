package com.carnicero.martin.juan.app.controller;

import com.carnicero.martin.juan.app.exception.CreatedException;
import com.carnicero.martin.juan.app.exception.DeletedException;
import com.carnicero.martin.juan.app.exception.UpdatedException;
import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.model.TipoComida;
import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.request.EditarComidaRequest;
import com.carnicero.martin.juan.app.request.RegistrarComida;
import com.carnicero.martin.juan.app.response.InformacionComida;
import com.carnicero.martin.juan.app.response.InformacionComidaUsuario;
import com.carnicero.martin.juan.app.service.interfaces.ComidaService;
import com.carnicero.martin.juan.app.service.interfaces.RecomendacionDiariaService;
import com.carnicero.martin.juan.app.util.converter.InformacionComidaConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity listarComidasUsuarioDia(@RequestParam String fecha) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario principal = (Usuario) authentication.getPrincipal();

            List<InformacionComida> informacion = comidaService.listarComidasUsuarioFecha(principal.getEmail(), fecha)
                    .stream()
                    .map(InformacionComidaConverter::comidaToInformacion).
                    collect(Collectors.toList());
            return ResponseEntity.ok(informacion.isEmpty() ? SIN_INFORMACION : informacion);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping(REGISTRAR)
    public ResponseEntity registrarComida(@RequestBody RegistrarComida data) {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                Usuario principal = (Usuario) authentication.getPrincipal();
                Comida comidaRegistrada = comidaService.registrarComida(data, principal);
                recomendacionDiariaService.actualizar(principal.getEmail());
                return ResponseEntity.ok(REGISTRAR_OK);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (CreatedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(EDITAR)
    public ResponseEntity editarComida(@RequestBody EditarComidaRequest data) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                Usuario principal = (Usuario) authentication.getPrincipal();
                Comida comidaEditada = comidaService.editarComida(data, principal);
                recomendacionDiariaService.actualizar(principal.getEmail());
                return ResponseEntity.ok(comidaEditada);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (UpdatedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping(ELIMINAR)
    public ResponseEntity eliminarComida(@RequestParam final String fechaDia, @RequestParam TipoComida tipoComida) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                Usuario principal = (Usuario) authentication.getPrincipal();
                comidaService.eliminarComida(fechaDia, principal.getEmail(), tipoComida);
                recomendacionDiariaService.actualizar(principal.getEmail());
                return ResponseEntity.ok(ELIMINAR_OK);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (DeletedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
