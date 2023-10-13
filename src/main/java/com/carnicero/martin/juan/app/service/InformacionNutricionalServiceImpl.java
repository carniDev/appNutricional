package com.carnicero.martin.juan.app.service;

import com.carnicero.martin.juan.app.model.InformacionNutricionalAlimento;
import com.carnicero.martin.juan.app.repository.InformacionNutricionalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
