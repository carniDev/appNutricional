package com.carnicero.martin.juan.app.util.converter;

import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.response.InformacionComida;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InformacionComidaConverter {
    public static InformacionComida comidaToInformacion(Comida data){
        InformacionComida informacionComida = new InformacionComida();
        informacionComida.setNombreUsuario(data.getUsuario().getNombre());
        informacionComida.setEmail(data.getUsuario().getEmail());
        informacionComida.setTipoComida(data.getTipoComida());
        informacionComida.setFechaComida(data.getFechaComida());
        informacionComida.setAlimentos(data.getListadoAlimentos());
        calcularNutritientes(informacionComida);
        return informacionComida;

    }


    private static void calcularNutritientes(InformacionComida comida){
        AtomicInteger kcal = new AtomicInteger();
        AtomicInteger proteinas = new AtomicInteger();
        AtomicInteger grasas = new AtomicInteger();
        AtomicInteger hidratos = new AtomicInteger();

        comida.getAlimentos().forEach((food ->{
            double cantidad = food.getCantidadAlimento();
            kcal.addAndGet((int) (cantidad * food.getInformacion().getKcal())/100);
            proteinas.addAndGet((int) (cantidad * food.getInformacion().getProteinas())/100);
            grasas.addAndGet((int) (cantidad * food.getInformacion().getGrasas())/100);
            hidratos.addAndGet((int) (cantidad * food.getInformacion().getHidratosCarbono())/100);


        }));

        comida.setKcal(kcal.get());
        comida.setProteinas(proteinas.get());
        comida.setGrasas(grasas.get());
        comida.setHidratosCarbono(hidratos.get());

    }
}
