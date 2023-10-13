package com.carnicero.martin.juan.app.util.generador;

import com.carnicero.martin.juan.app.repository.InformacionNutricionalRepository;

import java.util.Random;

public class GeneradorCodigo {

    public static String generarCodigoUnico(){
        return String.valueOf(new Random().nextInt(10000-1)+1);
    }

    public static boolean comprobarCodigoExistente(InformacionNutricionalRepository repository,String codigo){
        return repository.existsByCodigoAlimento(codigo);

    }
}
