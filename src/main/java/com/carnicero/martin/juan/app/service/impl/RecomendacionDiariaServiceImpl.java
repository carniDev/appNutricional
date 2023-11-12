package com.carnicero.martin.juan.app.service.impl;

import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.model.RecomendacionDiaria;
import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.repository.ComidaRepository;
import com.carnicero.martin.juan.app.repository.RecomendacionDiariaRepository;
import com.carnicero.martin.juan.app.repository.UsuarioRepository;
import com.carnicero.martin.juan.app.request.EditarComidaRequest;
import com.carnicero.martin.juan.app.request.RegistrarComida;
import com.carnicero.martin.juan.app.response.InformacionDiariaResponse;
import com.carnicero.martin.juan.app.response.MacroNutritientesComida;
import com.carnicero.martin.juan.app.service.interfaces.ComidaService;
import com.carnicero.martin.juan.app.service.interfaces.RecomendacionDiariaService;
import com.carnicero.martin.juan.app.service.interfaces.UsuarioService;
import com.carnicero.martin.juan.app.util.converter.LocalDateConverter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static com.carnicero.martin.juan.app.util.converter.LocalDateConverter.*;
import static com.carnicero.martin.juan.app.util.converter.LocalDateConverter.stringToLocalDateConverter;

@Service
public class RecomendacionDiariaServiceImpl implements RecomendacionDiariaService {

    private final RecomendacionDiariaRepository recomendacionRepository;
    private final UsuarioService usuarioService;
    private final ComidaRepository comidaRepository;

    public RecomendacionDiariaServiceImpl(RecomendacionDiariaRepository recomendacionRepository, UsuarioService usuarioService,
                                          ComidaRepository comidaRepository) {
        this.recomendacionRepository = recomendacionRepository;
        this.usuarioService = usuarioService;
        this.comidaRepository = comidaRepository;
    }

    private RecomendacionDiaria obtener(String fechaDia, String email) {
        return recomendacionRepository.findByFechaAndUsuarioEmail(stringToLocalDateConverter(fechaDia), email);
    }

    public InformacionDiariaResponse obtenerInformacion(String fechaDia, String email) {
        Usuario usuario = usuarioService.obtenerInformacionUsuario(email);
        RecomendacionDiaria recomendacion = recomendacionRepository.findByFechaAndUsuarioEmail(stringToLocalDateConverter(fechaDia), email);
        InformacionDiariaResponse diaria = new InformacionDiariaResponse();
        diaria.setHidratosCarbono(recomendacion.getHidratosCarbonoDiarios());
        diaria.setKcal(recomendacion.getKcalDiarias());
        diaria.setGrasas(recomendacion.getGrasaDiaria());
        diaria.setProteinas(recomendacion.getProteinaDiaria());
        List<Comida> comidas = comidaRepository.findAllByFechaComidaAndUsuarioEmail(stringToLocalDateConverter(fechaDia),email);
        if(comidas.isEmpty()){
            diaria.setComidas(new ArrayList<>());
        }else{
            diaria.setComidas(comidas);
        }

        return diaria;
    }

    public RecomendacionDiaria crearRecomendacionDiaria(Usuario usuario) {
        return recomendacionRepository.save(new RecomendacionDiaria(usuario));
    }

    public RecomendacionDiaria crearRecomendacionDiaria(String email) {
        Usuario usuario = usuarioService.obtenerInformacionUsuario(email);
        return recomendacionRepository.save(new RecomendacionDiaria(usuario));
    }


}
