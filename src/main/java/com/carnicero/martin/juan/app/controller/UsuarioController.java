package com.carnicero.martin.juan.app.controller;

import com.carnicero.martin.juan.app.exception.DeletedException;
import com.carnicero.martin.juan.app.exception.UpdatedException;
import com.carnicero.martin.juan.app.exception.UserNotFound;
import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.request.EditarUsuario;
import com.carnicero.martin.juan.app.service.interfaces.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.carnicero.martin.juan.app.util.Constantes.Constantes.*;

@RestController
@RequestMapping(NOMBRE_APP+"/"+USUARIO_CONTROLLER)
@RequiredArgsConstructor
public class UsuarioController {
    public UsuarioService usuarioService;


    @GetMapping(INFORMACION)
    public ResponseEntity informacionUsuario(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication!=null && authentication.isAuthenticated()){
                Usuario usuario = (Usuario)authentication.getPrincipal();
                return ResponseEntity.ok(usuario);
            }
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }catch (UserNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    @PutMapping(EDITAR)
    public ResponseEntity editarUsuario(@Valid @RequestBody EditarUsuario usuarioEditado){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication!=null && authentication.isAuthenticated()) {
                Usuario usuario =(Usuario) authentication.getPrincipal();
                Usuario usuarioParaEditar = usuarioService.editarUsuario(usuario, usuarioEditado);
                return ResponseEntity.ok(EDITAR_OK);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }catch (UpdatedException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @DeleteMapping(ELIMINAR)
    public ResponseEntity eliminarUsuario(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication!=null && authentication.isAuthenticated()) {
                Usuario usuario =(Usuario) authentication.getPrincipal();
                usuarioService.eliminarUsuario(usuario);
                return ResponseEntity.ok(ELIMINAR_OK);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }catch (DeletedException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
