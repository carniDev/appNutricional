package com.carnicero.martin.juan.app.util.converter;

import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.request.EditarUsuario;

public class UsuarioConverter {

    public static void editarUsuariotoUsuario(Usuario usuario, EditarUsuario usuarioParaEditar){
        usuario.setNombre(usuarioParaEditar.getNombre());
    }
}
