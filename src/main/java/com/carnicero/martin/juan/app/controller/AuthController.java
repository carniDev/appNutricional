package com.carnicero.martin.juan.app.controller;

import com.carnicero.martin.juan.app.request.LoginRequest;
import com.carnicero.martin.juan.app.request.RegisterRequest;
import com.carnicero.martin.juan.app.response.AuthResponse;
import com.carnicero.martin.juan.app.service.AuthService;
import com.carnicero.martin.juan.app.util.Constantes.Constantes;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.carnicero.martin.juan.app.util.Constantes.Constantes.*;
@RestController
@RequestMapping(NOMBRE_APP+"/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }

}
