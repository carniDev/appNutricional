package com.carnicero.martin.juan.app.service.impl;

import com.carnicero.martin.juan.app.model.RegistroLoginUsuario;
import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.repository.RegistroLoginUsuarioRepository;
import com.carnicero.martin.juan.app.service.interfaces.LoginService;
import com.carnicero.martin.juan.app.service.interfaces.UsuarioService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoginServiceImpl implements LoginService {

    private final RegistroLoginUsuarioRepository loginUserRepository;
    private final UsuarioService usuarioService;

    public LoginServiceImpl(RegistroLoginUsuarioRepository loginUserRepository, UsuarioService usuarioService) {
        this.loginUserRepository = loginUserRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    public LocalDateTime ultimaVezLogueadoUsuario(String email) {

        Usuario informacionUsuario = usuarioService.obtenerInformacionUsuario(email);
        RegistroLoginUsuario datosRegistro =loginUserRepository.findByUsuario(informacionUsuario).orElse(new RegistroLoginUsuario());
        if(datosRegistro.getUsuario()==null){
            primeraVezLogueado(informacionUsuario,datosRegistro);
        }
        return datosRegistro.getInicioSesion();
    }


    private void primeraVezLogueado(Usuario usuario, RegistroLoginUsuario datosRegistro){

        datosRegistro.setUsuario(usuario);
        datosRegistro.setInicioSesion(LocalDateTime.now());
        loginUserRepository.save(datosRegistro);
    }


}
