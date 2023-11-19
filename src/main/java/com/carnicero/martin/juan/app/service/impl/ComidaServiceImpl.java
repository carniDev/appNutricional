package com.carnicero.martin.juan.app.service.impl;

import com.carnicero.martin.juan.app.exception.CreatedException;
import com.carnicero.martin.juan.app.exception.DeletedException;
import com.carnicero.martin.juan.app.exception.UpdatedException;
import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.model.TipoComida;
import com.carnicero.martin.juan.app.model.Usuario;
import com.carnicero.martin.juan.app.repository.ComidaRepository;
import com.carnicero.martin.juan.app.repository.RecomendacionDiariaRepository;
import com.carnicero.martin.juan.app.request.EditarComidaRequest;
import com.carnicero.martin.juan.app.request.RegistrarComida;
import com.carnicero.martin.juan.app.service.interfaces.ComidaService;
import com.carnicero.martin.juan.app.service.interfaces.UsuarioService;
import com.carnicero.martin.juan.app.util.converter.LocalDateConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.carnicero.martin.juan.app.util.Constantes.Constantes.*;
import static com.carnicero.martin.juan.app.util.converter.ComidaConverter.registrarComidaToEntity;
import static com.carnicero.martin.juan.app.util.converter.LocalDateConverter.stringToLocalDateConverter;

@Service
public class ComidaServiceImpl implements ComidaService {

    private final ComidaRepository comidaRepository;
    private final UsuarioService usuarioService;

    private final RecomendacionDiariaRepository recomendacionDiariaRepository;

    public ComidaServiceImpl(ComidaRepository comidaRepository, UsuarioService usuarioService, RecomendacionDiariaRepository recomendacionDiariaRepository) {
        this.comidaRepository = comidaRepository;
        this.usuarioService = usuarioService;
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
            List<Comida>comidas = comidaRepository.findAllByFechaComidaAndUsuarioEmail(LocalDateConverter.stringToLocalDateConverter(data.getFechaComida()),data.getEmail());
            comidas.add(comidaParaRegistrar);
            return comidaRepository.save(comidaParaRegistrar);
        }
        throw new CreatedException(ERROR_REGISTRAR);
    }

    @Override
    public Comida editarComida(EditarComidaRequest data) {
        try {
            Comida comidaUsuario = listarUnaComidaUsuarioFecha(data.getEmail(), data.getFechaComida(), data.getTipoComida());
           Map<Long,Alimento>alimentos = data.getListadoAlimentos().stream().collect(Collectors.toMap(Alimento::getIdAlimento,food ->food));
            comidaUsuario.getListadoAlimentos().stream()
                    .filter(alimento -> alimentos.containsKey(alimento.getIdAlimento()))
                    .forEach(alimento -> {
                        if(alimentos.containsKey(alimento.getIdAlimento())){
                            Alimento alimentoParaEditar = alimentos.get(alimento.getIdAlimento());
                            alimento.setCantidadAlimento(alimentoParaEditar.getCantidadAlimento());
                            alimento.setInformacion(alimentoParaEditar.getInformacion());
                        }
                    });

            return comidaRepository.save(comidaUsuario);
        }catch (UpdatedException e){
            throw new UpdatedException(ERROR_EDITAR);
        }
    }

    @Override
    public void eliminarComida(String fechaDia,String email,TipoComida tipoComida) {
        try {
            Comida comidaParaEliminar = comidaRepository.findByFechaComidaAndUsuarioEmailAndTipoComida(LocalDateConverter.stringToLocalDateConverter(fechaDia), email, tipoComida).orElseThrow(() -> new RuntimeException("No se ha encontrado la comida"));
            comidaRepository.deleteById(comidaParaEliminar.getIdComida());
        }catch (DeletedException e){
            throw new DeletedException(ERROR_ELIMINAR);
        }
    }



}
