package com.carnicero.martin.juan.app.service.impl;

import com.carnicero.martin.juan.app.exception.CreatedException;
import com.carnicero.martin.juan.app.exception.DeletedException;
import com.carnicero.martin.juan.app.exception.UpdatedException;
import com.carnicero.martin.juan.app.exception.UserNotFound;
import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.repository.UsuarioRepository;
import com.carnicero.martin.juan.app.request.EditarUsuario;
import com.carnicero.martin.juan.app.request.RegistrarUsuario;
import com.carnicero.martin.juan.app.service.interfaces.UsuarioService;
import com.carnicero.martin.juan.app.util.converter.UsuarioConverter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UsuarioServiceImpl implements UsuarioService {

public final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario obtenerInformacionUsuario(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(()->new UserNotFound("No se ha encontrado el usuario"));
    }

    @Override
    public Usuario registrarUsuario(RegistrarUsuario usuario) {
        Usuario usuarioParaRegistrar = new Usuario(usuario);
        if(!usuarioRepository.existsByEmail(usuarioParaRegistrar.getEmail())){
            usuarioParaRegistrar.setFechaRegistro(LocalDateTime.now());
            return usuarioRepository.save(usuarioParaRegistrar);
        }
        throw new CreatedException("El email ya existe en la base de datos");

    }

    @Override
    public Usuario editarUsuario(String email,EditarUsuario usuario) {

        Usuario usuarioParaEditar = obtenerInformacionUsuario(email);
        try {
            UsuarioConverter.editarUsuariotoUsuario(usuarioParaEditar, usuario);
            return usuarioRepository.save(usuarioParaEditar);
        }catch (UpdatedException e) {
            throw new UpdatedException("No se ha podido actualizar la informacion");
        }
    }

    @Override
    public void eliminarUsuario(String email) {
        Usuario usuarioParaEliminar = obtenerInformacionUsuario(email);
        try{
            usuarioRepository.delete(usuarioParaEliminar);
        }catch (DeletedException e){
            throw new DeletedException("No se ha podido eliminar correctamente al usuario");
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
