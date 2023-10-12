package com.carnicero.martin.juan.app.controller;

import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.request.EditarUsuario;
import com.carnicero.martin.juan.app.request.RegistrarUsuario;
import com.carnicero.martin.juan.app.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app-nutricional")
public class UsuarioController {
    public UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/informacion-usuario")
    public ResponseEntity informacionUsuario(@RequestParam String email){
        try {
            Usuario usuarioObtenido = usuarioService.obtenerInformacionUsuario(email).orElseThrow(() -> new RuntimeException("No se ha encontrado ningun usuario"));
            return ResponseEntity.ok(usuarioObtenido);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/registrar-usuario")
    public ResponseEntity registrarUsuario(@Valid @RequestBody RegistrarUsuario usuario){
        try{
            Usuario usuarioRegistrado = usuarioService.registrarUsuario(usuario);
            return ResponseEntity.ok("Usuario registrado correctamente");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/editar-usuario")
    public ResponseEntity editarUsuario(@RequestParam String email,@Valid @RequestBody EditarUsuario usuario){
        try {
            Usuario usuarioEditado = usuarioService.editarUsuario(email,usuario);
            return ResponseEntity.ok("Usuario editado correctamente");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @DeleteMapping("/eliminar-usuario")
    public ResponseEntity eliminarUsuario(@RequestParam String email){
        try {
            usuarioService.eliminarUsuario(email);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
