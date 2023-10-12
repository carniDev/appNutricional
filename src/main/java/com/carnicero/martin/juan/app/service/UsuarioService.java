package com.carnicero.martin.juan.app.service;

import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.request.EditarUsuario;
import com.carnicero.martin.juan.app.request.RegistrarUsuario;

import java.util.Optional;

public interface UsuarioService {
    public Optional<Usuario> obtenerInformacionUsuario(String email);

    public Usuario registrarUsuario(RegistrarUsuario usuario);

    Usuario editarUsuario(String email,EditarUsuario usuario);

    void eliminarUsuario(String email);
}
