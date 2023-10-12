package com.carnicero.martin.juan.app.service;

import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.repository.UsuarioRepository;
import com.carnicero.martin.juan.app.request.RegistrarUsuario;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{

public final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<Usuario> obtenerInformacionUsuario(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Usuario registrarUsuario(RegistrarUsuario usuario) {
        Usuario usuarioParaRegistrar = new Usuario(usuario);
        usuarioParaRegistrar.setFechaRegistro(LocalDateTime.now());
        return usuarioRepository.save(usuarioParaRegistrar);
    }
}
