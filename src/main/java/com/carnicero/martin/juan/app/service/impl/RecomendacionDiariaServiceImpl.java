package com.carnicero.martin.juan.app.service.impl;

import com.carnicero.martin.juan.app.model.RecomendacionDiaria;
import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.repository.RecomendacionDiariaRepository;
import com.carnicero.martin.juan.app.service.interfaces.RecomendacionDiariaService;
import com.carnicero.martin.juan.app.service.interfaces.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.carnicero.martin.juan.app.util.converter.LocalDateConverter.stringToLocalDateConverter;

@Service
public class RecomendacionDiariaServiceImpl implements RecomendacionDiariaService {

    private final RecomendacionDiariaRepository recomendacionRepository;
    private final UsuarioService usuarioService;

    public RecomendacionDiariaServiceImpl(RecomendacionDiariaRepository recomendacionRepository, UsuarioService usuarioService) {
        this.recomendacionRepository = recomendacionRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    public RecomendacionDiaria obtenerInformacion(String fechaDia,String email) {

        Usuario usuario = usuarioService.obtenerInformacionUsuario(email);
        Optional<RecomendacionDiaria> recomendacion = recomendacionRepository.findByFechaAndUsuario(stringToLocalDateConverter(fechaDia),usuario);
        return recomendacion.orElseGet(() -> { return crearRecomendacionDiaria(usuario);

        });

    }

    public RecomendacionDiaria crearRecomendacionDiaria(Usuario usuario){
        return recomendacionRepository.save(new RecomendacionDiaria(usuario));
    }

    public RecomendacionDiaria crearRecomendacionDiaria(String email) {
        Usuario usuario = usuarioService.obtenerInformacionUsuario(email);
        return recomendacionRepository.save(new RecomendacionDiaria(usuario));
    }
}
