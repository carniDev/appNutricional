package com.carnicero.martin.juan.app.service;


import com.carnicero.martin.juan.app.model.Rol;
import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.repository.UsuarioRepository;
import com.carnicero.martin.juan.app.request.LoginRequest;
import com.carnicero.martin.juan.app.request.RegisterRequest;
import com.carnicero.martin.juan.app.response.AuthResponse;
import com.carnicero.martin.juan.app.util.converter.LocalDateConverter;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user = usuarioRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();

    }

    public AuthResponse register(RegisterRequest request) {
        Usuario user = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fechaNacimiento(LocalDateConverter.stringToLocalDateConverter(request.getFechaNacimiento()))
                .rol(Rol.USUARIO)
                .fechaRegistro(LocalDateTime.now())
                .build();


        usuarioRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();

    }


    public Boolean comprobarToken(String token, Usuario usuario) {

        return jwtService.isTokenValid(token,usuario);
    }
}
