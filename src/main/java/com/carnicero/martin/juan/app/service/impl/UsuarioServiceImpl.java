package com.carnicero.martin.juan.app.service.impl;

import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.repository.UsuarioRepository;
import com.carnicero.martin.juan.app.request.EditarUsuario;
import com.carnicero.martin.juan.app.request.RegistrarUsuario;
import com.carnicero.martin.juan.app.service.interfaces.UsuarioService;
import com.carnicero.martin.juan.app.util.converter.UsuarioConverter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsuarioServiceImpl implements UsuarioService {

public final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario obtenerInformacionUsuario(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(()->new RuntimeException("No se ha encontrado el usuario"));
    }

    @Override
    public Usuario registrarUsuario(RegistrarUsuario usuario) {
        Usuario usuarioParaRegistrar = new Usuario(usuario);
        if(!usuarioRepository.existsByEmail(usuarioParaRegistrar.getEmail())){
            usuarioParaRegistrar.setFechaRegistro(LocalDateTime.now());
            return usuarioRepository.save(usuarioParaRegistrar);
        }

        throw new RuntimeException("El email ya existe en la base de datos");

    }

    @Override
    public Usuario editarUsuario(String email,EditarUsuario usuario) {

        Usuario usuarioParaEditar = usuarioRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
        try {
            UsuarioConverter.editarUsuariotoUsuario(usuarioParaEditar, usuario);
            return usuarioRepository.save(usuarioParaEditar);
        }catch (RuntimeException e) {
            throw new RuntimeException("No se ha podido actualizar la informacion");
        }
    }

    @Override
    public void eliminarUsuario(String email) {
        Usuario usuarioParaEliminar = usuarioRepository.findByEmail(email).orElseThrow(()->new RuntimeException("Usuario no encontrado"));
        try{
            usuarioRepository.delete(usuarioParaEliminar);
        }catch (RuntimeException e){
            throw new RuntimeException("No se ha podido eliminar correctamente al usuario");
        }

    }

    public LocalDateTime ultimaVezLogueadoUsuario(String email) {
        Usuario informacionUsuario = obtenerInformacionUsuario(email);
        if(informacionUsuario.getInicioSesion()==null){
            primeraVezLogueado(informacionUsuario);
        }
        return informacionUsuario.getInicioSesion();
    }


    private void primeraVezLogueado(Usuario usuario){
        usuario.setInicioSesion(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }
}
