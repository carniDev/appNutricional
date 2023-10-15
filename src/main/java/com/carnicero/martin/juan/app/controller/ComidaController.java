package com.carnicero.martin.juan.app.controller;

import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.model.TipoComida;
import com.carnicero.martin.juan.app.request.EditarUnaComida;
import com.carnicero.martin.juan.app.request.RegistrarComida;
import com.carnicero.martin.juan.app.response.InformacionComida;
import com.carnicero.martin.juan.app.service.ComidaService;
import com.carnicero.martin.juan.app.util.converter.InformacionComidaConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("app-nutricional/comida")
public class ComidaController {

    private final ComidaService comidaService;

    public ComidaController(ComidaService comidaService) {
        this.comidaService = comidaService;
    }

    @GetMapping("/listar-una-comida")
    public ResponseEntity listarUnaComidaUsuarioDia(@RequestParam String fecha, @RequestParam String email, @RequestParam TipoComida tipoComida) {
        try {
            Comida comida = comidaService.listarUnaComidaUsuarioFecha(email, fecha, tipoComida);
            InformacionComida informacionComida = InformacionComidaConverter.comidaToInformacion(comida);
            /*ObjectMapper mapper = new ObjectMapper();
            mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            mapper.registerModule(new JavaTimeModule());
            String json = mapper.writeValueAsString(informacionComida);
            return ResponseEntity.ok(json);
*/
            return ResponseEntity.ok(informacionComida);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se han encontrado ninguna comida");
        }

    }


    @GetMapping("/listar-comidas")
    public ResponseEntity listarComidasUsuarioDia(@RequestParam String fecha, @RequestParam String email) {
        List<Comida> listarComidas = comidaService.listarComidasUsuarioFecha(email, fecha);
        List<InformacionComida>informacion = listarComidas.stream().map(InformacionComidaConverter::comidaToInformacion).collect(Collectors.toList());

        return ResponseEntity.ok(informacion.isEmpty() ? "No se han encontrado registros" : informacion);
    }

    @PostMapping("registrar")
    public ResponseEntity registrarComida(@RequestBody RegistrarComida data) {

        try {
            Comida comidaRegistrada = comidaService.registrarComida(data);
            return ResponseEntity.ok(comidaRegistrada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha registrado correctamente la comida");
        }
    }

    @PutMapping("editar")
    public ResponseEntity editarComida(@RequestBody EditarUnaComida data) {
        try {
            Comida comidaEditada = comidaService.editarComida(data);
            return ResponseEntity.ok(comidaEditada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha registrado correctamente la comida");
        }
    }

}
