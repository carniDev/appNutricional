package com.carnicero.martin.juan.app.service.impl;

import com.carnicero.martin.juan.app.model.*;
import com.carnicero.martin.juan.app.repository.ComidaRepository;
import com.carnicero.martin.juan.app.repository.RecomendacionDiariaRepository;
import com.carnicero.martin.juan.app.request.EditarUnaComida;
import com.carnicero.martin.juan.app.request.RegistrarComida;
import com.carnicero.martin.juan.app.service.interfaces.*;
import com.carnicero.martin.juan.app.util.converter.LocalDateConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.carnicero.martin.juan.app.util.converter.AlimentoConverter.*;
import static com.carnicero.martin.juan.app.util.converter.ComidaConverter.*;
import static com.carnicero.martin.juan.app.util.converter.LocalDateConverter.*;

@Service
public class ComidaServiceImpl implements ComidaService {

    private final ComidaRepository comidaRepository;
    private final InformacionNutricionalService informacionService;
    private final UsuarioService usuarioService;
    private final AlimentoService alimentoService;
    private final RecomendacionDiariaService recomendacionService;

    public ComidaServiceImpl(ComidaRepository comidaRepository, InformacionNutricionalService informacionService, UsuarioService usuarioService, AlimentoService alimentoService, RecomendacionDiariaService recomendacionService) {
        this.comidaRepository = comidaRepository;
        this.informacionService = informacionService;
        this.usuarioService = usuarioService;
        this.alimentoService = alimentoService;
        this.recomendacionService = recomendacionService;
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
        if (!comidaRepository.existsByFechaComidaAndUsuarioEmailAndTipoComida(data.getFecha(), data.getEmail(), data.getTipoComida())) {
            InformacionNutricionalAlimento informacion = informacionService.obtenerInformacion(data.getCodigoAlimento());
            Alimento alimentoARegistrar = registrarAlimentoToEntityComida(data.getCantidadComida(), informacion);
            Usuario usuario = usuarioService.obtenerInformacionUsuario(data.getEmail());
            Comida comidaParaRegistrar = registrarComidaToEntity(data, alimentoARegistrar, usuario);
            recomendacionService.actualizarPositivo(comidaParaRegistrar);
            return comidaRepository.save(comidaParaRegistrar);
        }
        throw new RuntimeException("No puedes registrar el mismo tipo de comida");
    }

    @Override
    public Comida editarComida(EditarUnaComida data) {
        Comida comidaUsuario = listarUnaComidaUsuarioFecha(data.getEmail(), data.getFecha(), data.getTipoComida());
        comidaUsuario.getListadoAlimentos().stream().filter(alimento ->
            alimento.getCantidadAlimento()!=data.getAlimento().getCantidadAlimento() && alimento.getInformacion()!=data.getAlimento().getInformacion()
        ).forEach(alimento -> {
            alimento.setCantidadAlimento(data.getAlimento().getCantidadAlimento());
            alimento.setInformacion(data.getAlimento().getInformacion());
        });

        recomendacionService.actualizarPositivo(comidaUsuario);
        return comidaRepository.save(comidaUsuario);
    }

    @Override
    public void eliminarComida(String fechaDia,String email,TipoComida tipoComida) {
        Comida comidaParaEliminar = comidaRepository.findByFechaComidaAndUsuarioEmailAndTipoComida(LocalDateConverter.stringToLocalDateConverter(fechaDia),email,tipoComida).orElseThrow(()->new RuntimeException("No se ha encontrado la comida"));
        comidaRepository.deleteById(comidaParaEliminar.getIdComida());
        recomendacionService.actualizarNegativo(comidaParaEliminar);
    }
}
