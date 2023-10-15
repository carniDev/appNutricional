package com.carnicero.martin.juan.app.controller;

import com.carnicero.martin.juan.app.service.interfaces.LoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("app-nutricional")
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/ultimo-loging")
    public LocalDateTime ultimaVezLogueado(@RequestParam String email){

        return loginService.ultimaVezLogueadoUsuario(email);


    }


}
