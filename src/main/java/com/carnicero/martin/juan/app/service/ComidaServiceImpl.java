package com.carnicero.martin.juan.app.service;

import com.carnicero.martin.juan.app.model.*;
import com.carnicero.martin.juan.app.repository.ComidaRepository;
import com.carnicero.martin.juan.app.request.EditarUnaComida;
import com.carnicero.martin.juan.app.request.RegistrarComida;
import com.carnicero.martin.juan.app.util.converter.AlimentoConverter;
import com.carnicero.martin.juan.app.util.converter.ComidaConverter;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.carnicero.martin.juan.app.util.converter.LocalDateConverter.*;

@Service
public class ComidaServiceImpl implements ComidaService {

    private final ComidaRepository comidaRepository;
    private final InformacionNutricionalService informacionService;
    private final UsuarioService usuarioService;

    public ComidaServiceImpl(ComidaRepository comidaRepository, InformacionNutricionalService informacionService, UsuarioService usuarioService) {
        this.comidaRepository = comidaRepository;
        this.informacionService = informacionService;
        this.usuarioService = usuarioService;
    }

    @Override
    public List<Comida> listarComidasUsuarioFecha(String email, String fecha) {
        return comidaRepository.findAllByFechaComidaAndUsuarioEmail(stringToLocalDateConverter(fecha), email);
    }
    public Comida listarUnaComidaUsuarioFecha(String email, String fecha, TipoComida tipoComida) {
        return comidaRepository.findByFechaComidaAndUsuarioEmailAndTipoComida(stringToLocalDateConverter(fecha), email,tipoComida).orElseThrow(()->new RuntimeException("No se ha encontrado la comida"));
    }

    @Override
    public Comida registrarComida(RegistrarComida data) {
        InformacionNutricionalAlimento informacion = informacionService.obtenerInformacion(data.getCodigoAlimento());
        Alimento alimentoARegistrar = AlimentoConverter.registrarAlimentoToEntityComida(data.getCantidadComida(), informacion);
        Usuario usuario = usuarioService.obtenerInformacionUsuario(data.getEmail());
        Comida comidaParaRegistrar = ComidaConverter.registrarComidaToEntity(data, alimentoARegistrar, usuario);
        return comidaRepository.save(comidaParaRegistrar);
    }

    @Override
    public Comida editarComida(EditarUnaComida data) {
        Comida comidaUsuario = listarUnaComidaUsuarioFecha(data.getEmail(), data.getFecha(),data.getTipoComida());
        List<Alimento>alimentosComida = data.getAlimentos();
        comidaUsuario.setListadoAlimentos(alimentosComida);
        return comidaRepository.save(comidaUsuario);
    }
}
