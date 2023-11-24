package com.carnicero.martin.juan.app.service.impl;

import com.carnicero.martin.juan.app.exception.DeletedException;
import com.carnicero.martin.juan.app.exception.UpdatedException;
import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.repository.UsuarioRepository;
import com.carnicero.martin.juan.app.request.EditarUsuario;
import com.carnicero.martin.juan.app.service.interfaces.UsuarioService;
import com.carnicero.martin.juan.app.util.converter.UsuarioConverter;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

public final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }



    @Override
    public Usuario editarUsuario(Usuario usuarioParaEditar,EditarUsuario usuarioEditado) {

        try {
            UsuarioConverter.editarUsuariotoUsuario(usuarioParaEditar, usuarioEditado);
            return usuarioRepository.save(usuarioParaEditar);
        }catch (UpdatedException e) {
            throw new UpdatedException("No se ha podido actualizar la informacion");
        }
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {

        try{
            usuarioRepository.delete(usuario);
        }catch (DeletedException e){
            throw new DeletedException("No se ha podido eliminar correctamente al usuario");
        }

    }



}
