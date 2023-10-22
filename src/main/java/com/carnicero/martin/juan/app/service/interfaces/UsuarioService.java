package com.carnicero.martin.juan.app.service.interfaces;

import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.request.EditarUsuario;
import com.carnicero.martin.juan.app.request.RegistrarUsuario;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UsuarioService {
    Usuario obtenerInformacionUsuario(String email);
    LocalDateTime ultimaVezLogueadoUsuario(String email);

    Usuario registrarUsuario(RegistrarUsuario usuario);

    Usuario editarUsuario(String email,EditarUsuario usuario);

    void eliminarUsuario(String email);
}
