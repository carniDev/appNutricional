package com.carnicero.martin.juan.app.service.impl;

import com.carnicero.martin.juan.app.exception.CreatedException;
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
import org.springframework.cglib.core.Local;
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
        RecomendacionDiaria recomendacion = recomendacionRepository.findByFechaAndUsuarioEmail(stringToLocalDateConverter(fechaDia), email);

        if(recomendacion==null){
            recomendacion=crearRecomendacionDiaria(email);
        }
        InformacionDiariaResponse diaria = asignarDatosInformacion(recomendacion);
        List<Comida> comidas = comidaRepository.findAllByFechaComidaAndUsuarioEmail(stringToLocalDateConverter(fechaDia),email);
        if(comidas.isEmpty()){
            diaria.setComidas(new ArrayList<>());
        }else{
            diaria.setComidas(comidas);
        }

        return diaria;
    }


    public RecomendacionDiaria crearRecomendacionDiaria(String email) {
        try {
            Usuario usuario = usuarioService.obtenerInformacionUsuario(email);
            return recomendacionRepository.save(new RecomendacionDiaria(usuario));
        }catch (CreatedException e){
            throw new CreatedException("No se ha podido crear");
        }
    }

    @Override
    public void actualizar(String email) {
        LocalDate fecha = LocalDate.now();
        actualizarMacronutrientes(fecha,email);
    }

    @Override
    public void actualizar(String email, String fecha) {
        LocalDate fechaConvertida = LocalDateConverter.stringToLocalDateConverter(fecha);
        actualizarMacronutrientes(fechaConvertida,email);
    }

    private InformacionDiariaResponse asignarDatosInformacion(RecomendacionDiaria recomendacion){
        InformacionDiariaResponse diaria = new InformacionDiariaResponse();
        diaria.setHidratosCarbono(recomendacion.getHidratosCarbonoDiarios());
        diaria.setKcal(recomendacion.getKcalDiarias());
        diaria.setGrasas(recomendacion.getGrasaDiaria());
        diaria.setProteinas(recomendacion.getProteinaDiaria());
        return diaria;
    }

    private void actualizarMacronutrientes(LocalDate fechaDia, String email) {
        RecomendacionDiaria recomendacionDiaria = recomendacionRepository.findByFechaAndUsuarioEmail(fechaDia,email);
        List<Comida>comidas = comidaRepository.findAllByFechaComidaAndUsuarioEmail(fechaDia,email);
        MacroNutritientesComida nutriente = calcular(comidas);
        recomendacionDiaria.setHidratosCarbonoDiarios(nutriente.getHidratosCarbono());
        recomendacionDiaria.setProteinaDiaria(nutriente.getProteinas());
        recomendacionDiaria.setGrasaDiaria(nutriente.getGrasas());
        recomendacionDiaria.setKcalDiarias(nutriente.getKcal());
        recomendacionRepository.save(recomendacionDiaria);
    }

    private MacroNutritientesComida calcular(List<Comida> comidas) {
        AtomicInteger hidratos = new AtomicInteger();
        AtomicInteger proteinas = new AtomicInteger();
        AtomicInteger grasas = new AtomicInteger();
        comidas.forEach(comida -> {
            comida.getListadoAlimentos().forEach(alimento -> {
                double cantidad = alimento.getCantidadAlimento();
                hidratos.addAndGet((int) (cantidad * alimento.getInformacion().getHidratosCarbono()) / 100);
                proteinas.addAndGet((int) (cantidad * alimento.getInformacion().getProteinas()) / 100);
                grasas.addAndGet((int) (cantidad * alimento.getInformacion().getGrasas()) / 100);
            });
        });

        MacroNutritientesComida nutrientesComida = new MacroNutritientesComida();
        nutrientesComida.setHidratosCarbono(hidratos.get());
        nutrientesComida.setProteinas(proteinas.get());
        nutrientesComida.setGrasas(grasas.get());
        nutrientesComida.setKcal(calculoKcal(nutrientesComida));
        return nutrientesComida;
    }

    private int calculoKcal(MacroNutritientesComida nutrientes) {
        int kcal = 0;
        kcal += (nutrientes.getHidratosCarbono() * 4) + (nutrientes.getProteinas() * 4) + (nutrientes.getGrasas() * 9);
        return kcal;
    }


}
