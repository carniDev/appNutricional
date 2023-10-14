package com.carnicero.martin.juan.app.service;

import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.model.InformacionNutricionalAlimento;
import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.repository.ComidaRepository;
import com.carnicero.martin.juan.app.request.RegistrarComida;
import com.carnicero.martin.juan.app.util.converter.AlimentoConverter;
import com.carnicero.martin.juan.app.util.converter.LocalDateConverter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.carnicero.martin.juan.app.util.converter.LocalDateConverter.*;

@Service
public class ComidaServiceImpl implements ComidaService{

    private final ComidaRepository comidaRepository;
    private final InformacionNutricionalService informacionService;
    private final UsuarioService usuarioService;

    public ComidaServiceImpl(ComidaRepository comidaRepository, InformacionNutricionalService informacionService, UsuarioService usuarioService) {
        this.comidaRepository = comidaRepository;
        this.informacionService = informacionService;
        this.usuarioService = usuarioService;
    }

    @Override
    public List<?> listarComidasUsuarioFecha(String email, String fecha) {
        return comidaRepository.findAllByFechaComidaAndUsuarioEmail(stringToLocalDateConverter(fecha),email);
    }

    @Override
    public Comida registrarComida(RegistrarComida data) {
        InformacionNutricionalAlimento informacion = informacionService.obtenerInformacion(data.getCodigoAlimento());
        Alimento alimentoARegistrar = AlimentoConverter.registrarAlimentoToEntityComida(data.getCantidadComida(),informacion);
        Usuario usuario = usuarioService.obtenerInformacionUsuario(data.getEmail());
        Comida comidaParaRegistrar = new Comida();
        comidaParaRegistrar.setFechaComida(LocalDate.now());
        comidaParaRegistrar.setTipoComida(data.getTipoComida());
        comidaParaRegistrar.setUsuario(usuario);
        comidaParaRegistrar.getListadoAlimentos().add(alimentoARegistrar);
        return comidaRepository.save(comidaParaRegistrar);
    }
}
