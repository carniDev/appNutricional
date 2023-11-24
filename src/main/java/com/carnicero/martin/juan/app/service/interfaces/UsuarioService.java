package com.carnicero.martin.juan.app.service.interfaces;

import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.request.EditarUsuario;

public interface UsuarioService {


    Usuario editarUsuario(Usuario usuarioParaEditar,EditarUsuario usuarioEditado);

    void eliminarUsuario(Usuario usuario);
}
