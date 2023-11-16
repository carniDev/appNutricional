package com.carnicero.martin.juan.app.service.impl;

import com.carnicero.martin.juan.app.exception.CreatedException;
import com.carnicero.martin.juan.app.exception.DeletedException;
import com.carnicero.martin.juan.app.exception.UpdatedException;
import com.carnicero.martin.juan.app.model.InformacionNutricionalAlimento;
import com.carnicero.martin.juan.app.repository.InformacionNutricionalRepository;
import com.carnicero.martin.juan.app.request.EditarInformacionNutricional;
import com.carnicero.martin.juan.app.request.InformacionNutricional;
import com.carnicero.martin.juan.app.service.interfaces.InformacionNutricionalService;
import com.carnicero.martin.juan.app.util.converter.InformacionNutricionalConverter;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.carnicero.martin.juan.app.util.Constantes.Constantes.*;
import static com.carnicero.martin.juan.app.util.converter.InformacionNutricionalConverter.infomacionNutricionalToEntity;
import static com.carnicero.martin.juan.app.util.generador.GeneradorCodigo.comprobarCodigoExistente;
import static com.carnicero.martin.juan.app.util.generador.GeneradorCodigo.generarCodigoUnico;

@Service
public class InformacionNutricionalServiceImpl implements InformacionNutricionalService {

    private final InformacionNutricionalRepository informacionRepository;

    public InformacionNutricionalServiceImpl(InformacionNutricionalRepository informacionRepository) {
        this.informacionRepository = informacionRepository;
    }

    @Override
    public InformacionNutricionalAlimento obtenerInformacion(String codigoAlimento) {
        return informacionRepository.findByCodigoAlimento(codigoAlimento).orElseThrow(()->new RuntimeException(NO_ENCONTRADO));
    }

    @Override
    public List<InformacionNutricionalAlimento> obtenerInformacion() {
        return informacionRepository.findAll();
    }

    @Override
    public List<?> obtenerInformacionByNombre(String nombre) {
        return informacionRepository.findByNombreContaining(nombre);
    }

    @Override
    public InformacionNutricionalAlimento registrarAlimento(InformacionNutricional data) {
        try {
            InformacionNutricionalAlimento alimentoParaRegistrar = infomacionNutricionalToEntity(data);
            String codigo = generarCodigoUnico();
            while (comprobarCodigoExistente(informacionRepository, codigo)) {
                codigo = generarCodigoUnico();
            }
            alimentoParaRegistrar.setCodigoAlimento(codigo);
            return informacionRepository.save(alimentoParaRegistrar);
        }catch (CreatedException e){
            throw new CreatedException(ERROR_REGISTRAR);
        }
    }

    @Override
    public InformacionNutricionalAlimento editarAlimento(String codigo,EditarInformacionNutricional data) {
        try{
        InformacionNutricionalAlimento alimentoParaEditar = obtenerInformacion(codigo);
        InformacionNutricionalConverter.editarInfomacionNutricionalToEntity(alimentoParaEditar,data);
        return informacionRepository.save(alimentoParaEditar);
        }catch (UpdatedException e){
            throw new UpdatedException(ERROR_EDITAR);
        }

    }

    @Override
    public void eliminarAlimento(String codigo) {
        try{
        InformacionNutricionalAlimento alimentoParaEliminar = obtenerInformacion(codigo);
        informacionRepository.delete(alimentoParaEliminar);
        }catch (DeletedException e){
            throw new DeletedException(ERROR_ELIMINAR);
        }
    }
}
