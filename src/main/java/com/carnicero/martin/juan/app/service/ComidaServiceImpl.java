package com.carnicero.martin.juan.app.service;

import com.carnicero.martin.juan.app.repository.ComidaRepository;
import com.carnicero.martin.juan.app.util.converter.LocalDateConverter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.carnicero.martin.juan.app.util.converter.LocalDateConverter.*;

@Service
public class ComidaServiceImpl implements ComidaService{

    private final ComidaRepository comidaRepository;

    public ComidaServiceImpl(ComidaRepository comidaRepository) {
        this.comidaRepository = comidaRepository;
    }

    @Override
    public List<?> listarComidasUsuarioFecha(String email, String fecha) {
        return comidaRepository.findAllByFechaComidaAndUsuarioEmail(stringToLocalDateConverter(fecha),email);
    }
}
