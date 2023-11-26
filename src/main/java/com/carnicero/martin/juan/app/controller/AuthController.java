package com.carnicero.martin.juan.app.controller;

import com.carnicero.martin.juan.app.request.LoginRequest;
import com.carnicero.martin.juan.app.request.RegisterRequest;
import com.carnicero.martin.juan.app.response.AuthResponse;
import com.carnicero.martin.juan.app.service.AuthService;
import com.carnicero.martin.juan.app.util.Constantes.Constantes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.carnicero.martin.juan.app.util.Constantes.Constantes.*;

@RestController
@RequestMapping(NOMBRE_APP + "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) throws Exception {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequest request) throws Exception {

        return ResponseEntity.ok(authService.register(request));

    }

}
