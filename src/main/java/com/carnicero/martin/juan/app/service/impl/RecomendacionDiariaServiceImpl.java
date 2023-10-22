package com.carnicero.martin.juan.app.service.impl;

import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.model.RecomendacionDiaria;
import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.repository.RecomendacionDiariaRepository;
import com.carnicero.martin.juan.app.response.MacroNutritientesComida;
import com.carnicero.martin.juan.app.service.interfaces.RecomendacionDiariaService;
import com.carnicero.martin.juan.app.service.interfaces.UsuarioService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

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

    public RecomendacionDiaria obtenerInformacion(LocalDate fechaDia, String email){
        Usuario usuario = usuarioService.obtenerInformacionUsuario(email);
        Optional<RecomendacionDiaria> recomendacion = recomendacionRepository.findByFechaAndUsuario(fechaDia,usuario);
        return recomendacion.orElseGet(() -> { return crearRecomendacionDiaria(usuario);
        });
    }

    @Override
    public RecomendacionDiaria actualizarPositivo(Comida comida) {
        RecomendacionDiaria recomendacionDiaria = obtenerInformacion(comida.getFechaComida(),comida.getUsuario().getEmail());
        MacroNutritientesComida nutriente = calcular(comida.getListadoAlimentos());
        recomendacionDiaria.setHidratosCarbonoDiarios(recomendacionDiaria.getHidratosCarbonoDiarios()+nutriente.getHidratosCarbono());
        recomendacionDiaria.setProteinaDiaria(recomendacionDiaria.getProteinaDiaria()+nutriente.getProteinas());
        recomendacionDiaria.setGrasaDiaria(recomendacionDiaria.getGrasaDiaria()+nutriente.getGrasas());
        recomendacionDiaria.setKcalDiarias(recomendacionDiaria.getKcalDiarias()+nutriente.getKcal());
        return recomendacionRepository.save(recomendacionDiaria);
    }

    @Override
    public RecomendacionDiaria actualizarNegativo(Comida comida) {
        RecomendacionDiaria recomendacionDiaria = obtenerInformacion(comida.getFechaComida(),comida.getUsuario().getEmail());
        MacroNutritientesComida nutriente = calcular(comida.getListadoAlimentos());
        recomendacionDiaria.setHidratosCarbonoDiarios(recomendacionDiaria.getHidratosCarbonoDiarios()-nutriente.getHidratosCarbono());
        recomendacionDiaria.setProteinaDiaria(recomendacionDiaria.getProteinaDiaria()-nutriente.getProteinas());
        recomendacionDiaria.setGrasaDiaria(recomendacionDiaria.getGrasaDiaria()-nutriente.getGrasas());
        recomendacionDiaria.setKcalDiarias(recomendacionDiaria.getKcalDiarias()-nutriente.getKcal());
        return recomendacionRepository.save(recomendacionDiaria);
    }

    public RecomendacionDiaria crearRecomendacionDiaria(Usuario usuario){
        return recomendacionRepository.save(new RecomendacionDiaria(usuario));
    }

    public RecomendacionDiaria crearRecomendacionDiaria(String email) {
        Usuario usuario = usuarioService.obtenerInformacionUsuario(email);
        return recomendacionRepository.save(new RecomendacionDiaria(usuario));
    }

    private MacroNutritientesComida calcular(List<Alimento> alimentos){
        AtomicInteger hidratos = new AtomicInteger();
        AtomicInteger proteinas = new AtomicInteger();;
        AtomicInteger grasas=new AtomicInteger();;

        alimentos.forEach(alimento -> {
            double cantidad =alimento.getCantidadAlimento();
            hidratos.addAndGet((int) (cantidad * alimento.getInformacion().getHidratosCarbono())/100);
            proteinas.addAndGet((int) (cantidad * alimento.getInformacion().getProteinas())/100);
            grasas.addAndGet((int) (cantidad * alimento.getInformacion().getGrasas())/100);

        });
        MacroNutritientesComida nutrientesComida = new MacroNutritientesComida();
        nutrientesComida.setHidratosCarbono(hidratos.get());
        nutrientesComida.setProteinas(proteinas.get());
        nutrientesComida.setGrasas(grasas.get());
        nutrientesComida.setKcal(calculoKcal(nutrientesComida));
        return nutrientesComida;
    }

    private int calculoKcal(MacroNutritientesComida nutrientes){
        int kcal = 0;
        kcal+=(nutrientes.getHidratosCarbono()*4)+(nutrientes.getProteinas()*4)+(nutrientes.getGrasas()*9);
        return kcal;
    }
}
