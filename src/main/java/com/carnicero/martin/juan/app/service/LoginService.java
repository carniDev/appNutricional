package com.carnicero.martin.juan.app.service;

import java.time.LocalDateTime;

public interface LoginService {


    LocalDateTime ultimaVezLogueadoUsuario(String email);
}
