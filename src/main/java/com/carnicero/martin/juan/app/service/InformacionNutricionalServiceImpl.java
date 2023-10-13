package com.carnicero.martin.juan.app.service;

import com.carnicero.martin.juan.app.model.InformacionNutricionalAlimento;
import com.carnicero.martin.juan.app.repository.InformacionNutricionalRepository;
import com.carnicero.martin.juan.app.request.EditarInformacionNutricional;
import com.carnicero.martin.juan.app.request.InformacionNutricional;
import com.carnicero.martin.juan.app.util.converter.InformacionNutricionalConverter;
import com.carnicero.martin.juan.app.util.generador.GeneradorCodigo;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.carnicero.martin.juan.app.util.converter.InformacionNutricionalConverter.infomacionNutricionalToEntity;
import static com.carnicero.martin.juan.app.util.generador.GeneradorCodigo.*;

@Service
public class InformacionNutricionalServiceImpl implements  InformacionNutricionalService{

    private final InformacionNutricionalRepository informacionRepository;

    public InformacionNutricionalServiceImpl(InformacionNutricionalRepository informacionRepository) {
        this.informacionRepository = informacionRepository;
    }

    @Override
    public List<InformacionNutricionalAlimento> obtenerInformacion() {
        return informacionRepository.findAll();
    }

    @Override
    public InformacionNutricionalAlimento registrarAlimento(InformacionNutricional data) {
        InformacionNutricionalAlimento alimentoParaRegistrar = infomacionNutricionalToEntity(data);
        String codigo = generarCodigoUnico();
        while(comprobarCodigoExistente(informacionRepository,codigo)){
            codigo = generarCodigoUnico();
        }
        alimentoParaRegistrar.setCodigoAlimento(codigo);
        return informacionRepository.save(alimentoParaRegistrar);
    }

    @Override
    public InformacionNutricionalAlimento editarAlimento(String codigo,EditarInformacionNutricional data) {
        InformacionNutricionalAlimento alimentoParaEditar = informacionRepository.findByCodigoAlimento(codigo).orElseThrow(()->new RuntimeException("No se ha encontrado ningun alimento con ese codigo"));
        InformacionNutricionalConverter.editarInfomacionNutricionalToEntity(alimentoParaEditar,data);
        return informacionRepository.save(alimentoParaEditar);
    }

    @Override
    public void eliminarAlimento(String codigo) {
        InformacionNutricionalAlimento alimentoParaEliminar = informacionRepository.findByCodigoAlimento(codigo).orElseThrow(()->new RuntimeException("No se ha encontrado ningun alimento con ese codigo"));
        informacionRepository.delete(alimentoParaEliminar);
    }
}
