package com.carnicero.martin.juan.app.service.impl;

import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.model.InformacionNutricionalAlimento;
import com.carnicero.martin.juan.app.repository.AlimentoRepository;
import com.carnicero.martin.juan.app.request.EditarAlimento;
import com.carnicero.martin.juan.app.request.RegistrarAlimento;
import com.carnicero.martin.juan.app.service.interfaces.AlimentoService;
import com.carnicero.martin.juan.app.service.interfaces.InformacionNutricionalService;
import com.carnicero.martin.juan.app.util.converter.AlimentoConverter;
import org.springframework.stereotype.Service;

@Service
public class AlimentoServiceImpl implements AlimentoService {
    private final AlimentoRepository alimentoRepository;
    private final InformacionNutricionalService informacionService;

    public AlimentoServiceImpl(AlimentoRepository alimentoRepository, InformacionNutricionalService informacionService) {
        this.alimentoRepository = alimentoRepository;
        this.informacionService = informacionService;
    }

    @Override
    public Alimento buscarAlimento(Long id) {
        return alimentoRepository.findById(id).orElseThrow(()->new RuntimeException("No se ha encontrado el alimento"));
    }

    @Override
    public Alimento registrarAlimento(RegistrarAlimento data) {
        InformacionNutricionalAlimento informacion = informacionService.obtenerInformacion(data.getCodigoAlimento());
        Alimento alimentoParaRegistrar = AlimentoConverter.registrarAlimentoToEntity(data, informacion);
        return alimentoRepository.save(alimentoParaRegistrar);
    }

    public Alimento editarAlimento(EditarAlimento data) {
       Alimento alimento = buscarAlimento(data.getIdAlimento());
        InformacionNutricionalAlimento informacion = informacionService.obtenerInformacion(data.getCodigoAlimento());
        AlimentoConverter.editarAlimentoToEntity(data,alimento,informacion);
        return alimento;
    }


    public Alimento editarAlimento(Alimento original,Alimento data) {
        Alimento alimento = buscarAlimento(original.getIdAlimento());
        AlimentoConverter.alimentoEditadoToEntity(alimento,data);
        return alimento;
    }
}
