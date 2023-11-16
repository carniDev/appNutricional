package com.carnicero.martin.juan.app.controller;

import com.carnicero.martin.juan.app.exception.CreatedException;
import com.carnicero.martin.juan.app.exception.DeletedException;
import com.carnicero.martin.juan.app.exception.UpdatedException;
import com.carnicero.martin.juan.app.exception.UserNotFound;
import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.request.EditarUsuario;
import com.carnicero.martin.juan.app.request.RegistrarUsuario;
import com.carnicero.martin.juan.app.service.interfaces.UsuarioService;
import com.carnicero.martin.juan.app.util.Constantes.Constantes;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.carnicero.martin.juan.app.util.Constantes.Constantes.*;

@RestController
@RequestMapping(NOMBRE_APP+"/"+USUARIO_CONTROLLER)
public class UsuarioController {
    public UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping(INFORMACION)
    public ResponseEntity informacionUsuario(@RequestParam String email){
        try {
            Usuario usuarioObtenido = usuarioService.obtenerInformacionUsuario(email);
            return ResponseEntity.ok(usuarioObtenido);
        }catch (UserNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/ultimo-loging")
    public LocalDateTime ultimaVezLogueado(@RequestParam String email) {

        return usuarioService.ultimaVezLogueadoUsuario(email);
    }

    @PostMapping(REGISTRAR)
    public ResponseEntity registrarUsuario(@Valid @RequestBody RegistrarUsuario usuario){
        try{
            Usuario usuarioRegistrado = usuarioService.registrarUsuario(usuario);
            return ResponseEntity.ok(REGISTRAR_OK);
        }catch (CreatedException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(EDITAR)
    public ResponseEntity editarUsuario(@RequestParam String email,@Valid @RequestBody EditarUsuario usuario){
        try {
            Usuario usuarioEditado = usuarioService.editarUsuario(email,usuario);
            return ResponseEntity.ok(EDITAR_OK);
        }catch (UpdatedException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @DeleteMapping(ELIMINAR)
    public ResponseEntity eliminarUsuario(@RequestParam String email){
        try {
            usuarioService.eliminarUsuario(email);
            return ResponseEntity.ok(ELIMINAR_OK);
        }catch (DeletedException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
