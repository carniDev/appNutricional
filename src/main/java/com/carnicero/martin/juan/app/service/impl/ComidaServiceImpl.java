package com.carnicero.martin.juan.app.service.impl;

import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.model.TipoComida;
import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.repository.ComidaRepository;
import com.carnicero.martin.juan.app.request.EditarUnaComida;
import com.carnicero.martin.juan.app.request.RegistrarComida;
import com.carnicero.martin.juan.app.service.interfaces.*;
import com.carnicero.martin.juan.app.util.converter.LocalDateConverter;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.carnicero.martin.juan.app.util.Constantes.Constantes.*;
import static com.carnicero.martin.juan.app.util.converter.ComidaConverter.registrarComidaToEntity;
import static com.carnicero.martin.juan.app.util.converter.LocalDateConverter.stringToLocalDateConverter;

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
        if (!comidaRepository.existsByFechaComidaAndUsuarioEmailAndTipoComida(LocalDateConverter.stringToLocalDateConverter(data.getFechaComida()), data.getEmail(), data.getTipoComida())) {
            Usuario usuario = usuarioService.obtenerInformacionUsuario(data.getEmail());
            Comida comidaParaRegistrar = registrarComidaToEntity(data,usuario);
            recomendacionService.actualizarPositivo(comidaParaRegistrar);
            return comidaRepository.save(comidaParaRegistrar);
        }
        throw new RuntimeException(ERROR_REGISTRAR);
    }

    @Override
    public Comida editarComida(EditarUnaComida data) {
        try {
            Comida comidaUsuario = listarUnaComidaUsuarioFecha(data.getEmail(), data.getFecha(), data.getTipoComida());
            comidaUsuario.getListadoAlimentos().stream().filter(alimento ->
                    alimento.getCantidadAlimento() != data.getAlimento().getCantidadAlimento() && alimento.getInformacion() != data.getAlimento().getInformacion()
            ).forEach(alimento -> {
                alimento.setCantidadAlimento(data.getAlimento().getCantidadAlimento());
                alimento.setInformacion(data.getAlimento().getInformacion());
            });

            recomendacionService.actualizarPositivo(comidaUsuario);
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
            recomendacionService.actualizarNegativo(comidaParaEliminar);
        }catch (RuntimeException e){
            throw new RuntimeException(ERROR_ELIMINAR);
        }
    }
}
