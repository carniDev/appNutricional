package com.carnicero.martin.juan.app.service.impl;

import com.carnicero.martin.juan.app.model.*;
import com.carnicero.martin.juan.app.repository.ComidaRepository;
import com.carnicero.martin.juan.app.repository.RecomendacionDiariaRepository;
import com.carnicero.martin.juan.app.request.EditarUnaComida;
import com.carnicero.martin.juan.app.request.RegistrarComida;
import com.carnicero.martin.juan.app.service.interfaces.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
            recomendacionService.actualizar(comidaParaRegistrar);
            return comidaRepository.save(comidaParaRegistrar);
        }
        throw new RuntimeException("No puedes registrar el mismo tipo de comida");
    }

    @Override
    public Comida editarComida(EditarUnaComida data) {
        Comida comidaUsuario = listarUnaComidaUsuarioFecha(data.getEmail(), data.getFecha(), data.getTipoComida());
        data.getAlimentos().stream().map(alimentoEditado -> {
            List<Alimento> alimentos = new ArrayList<>();
            for (Alimento alimentoOriginal : comidaUsuario.getListadoAlimentos()) {
                alimentos.add(alimentoService.editarAlimento(alimentoOriginal, alimentoEditado));
            }
            return alimentos;
        }).collect(Collectors.toList());

        return comidaRepository.save(comidaUsuario);
    }

    @Override
    public void eliminarComida(Long id) {
        Comida comidaParaEliminar = comidaRepository.findById(id).orElseThrow(()->new RuntimeException("No se ha encontrado la comida"));
        comidaRepository.delete(comidaParaEliminar);
    }
}
