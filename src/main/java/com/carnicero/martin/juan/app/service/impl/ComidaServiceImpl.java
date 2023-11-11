package com.carnicero.martin.juan.app.service.impl;

import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.model.RecomendacionDiaria;
import com.carnicero.martin.juan.app.model.TipoComida;
import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.repository.ComidaRepository;
import com.carnicero.martin.juan.app.repository.RecomendacionDiariaRepository;
import com.carnicero.martin.juan.app.request.EditarComidaRequest;
import com.carnicero.martin.juan.app.request.RegistrarComida;
import com.carnicero.martin.juan.app.response.MacroNutritientesComida;
import com.carnicero.martin.juan.app.service.interfaces.*;
import com.carnicero.martin.juan.app.util.converter.LocalDateConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.carnicero.martin.juan.app.util.Constantes.Constantes.*;
import static com.carnicero.martin.juan.app.util.converter.ComidaConverter.registrarComidaToEntity;
import static com.carnicero.martin.juan.app.util.converter.LocalDateConverter.stringToLocalDateConverter;

@Service
public class ComidaServiceImpl implements ComidaService {

    private final ComidaRepository comidaRepository;
    private final InformacionNutricionalService informacionService;
    private final UsuarioService usuarioService;
    private final AlimentoService alimentoService;
    private final RecomendacionDiariaRepository recomendacionDiariaRepository;

    public ComidaServiceImpl(ComidaRepository comidaRepository, InformacionNutricionalService informacionService, UsuarioService usuarioService, AlimentoService alimentoService, RecomendacionDiariaRepository recomendacionDiariaRepository) {
        this.comidaRepository = comidaRepository;
        this.informacionService = informacionService;
        this.usuarioService = usuarioService;
        this.alimentoService = alimentoService;
        this.recomendacionDiariaRepository = recomendacionDiariaRepository;
    }

    @Override
    public List<Comida> listarComidasUsuarioFecha(String email, String fecha) {
        return comidaRepository.findAllByFechaComidaAndUsuarioEmail(stringToLocalDateConverter(fecha), email);
    }

    public Comida listarUnaComidaUsuarioFecha(String email, String fecha, TipoComida tipoComida) {
        return comidaRepository.findByFechaComidaAndUsuarioEmailAndTipoComida(stringToLocalDateConverter(fecha), email, tipoComida).orElseThrow(() -> new RuntimeException("No se ha encontrado la comida"));
    }

    @Override
    public Comida registrarComida(RegistrarComida data) {
        if (!comidaRepository.existsByFechaComidaAndUsuarioEmailAndTipoComida(LocalDateConverter.stringToLocalDateConverter(data.getFechaComida()), data.getEmail(), data.getTipoComida())) {
            Usuario usuario = usuarioService.obtenerInformacionUsuario(data.getEmail());
            Comida comidaParaRegistrar = registrarComidaToEntity(data,usuario);
            actualizarMacronutrientes(data.getFechaComida(),data.getEmail());
            return comidaRepository.save(comidaParaRegistrar);
        }
        throw new RuntimeException(ERROR_REGISTRAR);
    }

    @Override
    public Comida editarComida(EditarComidaRequest data) {
        try {
            Comida comidaUsuario = listarUnaComidaUsuarioFecha(data.getEmail(), data.getFechaComida(), data.getTipoComida());
            comidaUsuario.setListadoAlimentos(data.getListadoAlimentos());
            actualizarMacronutrientes(data.getFechaComida(),data.getEmail());
            return comidaRepository.save(comidaUsuario);
        }catch (RuntimeException e){
            throw new RuntimeException(ERROR_EDITAR);
        }
    }

    @Override
    public void eliminarComida(String fechaDia,String email,TipoComida tipoComida) {
        try {
            Comida comidaParaEliminar = comidaRepository.findByFechaComidaAndUsuarioEmailAndTipoComida(LocalDateConverter.stringToLocalDateConverter(fechaDia), email, tipoComida).orElseThrow(() -> new RuntimeException("No se ha encontrado la comida"));
            comidaRepository.deleteById(comidaParaEliminar.getIdComida());
            actualizarMacronutrientes(fechaDia, email);
        }catch (RuntimeException e){
            throw new RuntimeException(ERROR_ELIMINAR);
        }
    }

    private RecomendacionDiaria actualizarMacronutrientes(String fechaDia, String email) {
        RecomendacionDiaria recomendacionDiaria = recomendacionDiariaRepository.findByFechaAndUsuarioEmail(LocalDateConverter.stringToLocalDateConverter(fechaDia),email);
        List<Comida>comidas = comidaRepository.findAllByFechaComidaAndUsuarioEmail(stringToLocalDateConverter(fechaDia),email);
        MacroNutritientesComida nutriente = calcular(comidas);
        recomendacionDiaria.setHidratosCarbonoDiarios(nutriente.getHidratosCarbono());
        recomendacionDiaria.setProteinaDiaria(nutriente.getProteinas());
        recomendacionDiaria.setGrasaDiaria(nutriente.getGrasas());
        recomendacionDiaria.setKcalDiarias(nutriente.getKcal());
        return recomendacionDiariaRepository.save(recomendacionDiaria);
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
