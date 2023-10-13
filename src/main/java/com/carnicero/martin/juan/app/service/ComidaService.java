package com.carnicero.martin.juan.app.service;

import java.time.LocalDate;
import java.util.List;

public interface ComidaService {
    List<?> listarComidasUsuarioFecha(String email, String fecha);
}
