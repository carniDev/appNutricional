package com.carnicero.martin.juan.app.util.converter;

import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.response.InformacionComida;
import com.carnicero.martin.juan.app.response.InformacionComidaUsuario;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InformacionComidaConverter {
    public static InformacionComida comidaToInformacion(Comida data){
        InformacionComida informacionComida = new InformacionComida();
        informacionComida.setEmail(data.getUsuario().getUsername());
        informacionComida.setTipoComida(data.getTipoComida());
        informacionComida.setFechaComida(LocalDateConverter.localDateToString(data.getFechaComida()));
        informacionComida.setAlimentos(data.getListadoAlimentos());
        calcularNutritientes(informacionComida);
        return informacionComida;
    }

    public static InformacionComidaUsuario comidaToInformacionUsuario(Comida data){
        InformacionComidaUsuario informacion = new InformacionComidaUsuario();
        informacion.setIdComida(data.getIdComida());
        informacion.setTipoComida(data.getTipoComida().name());
        informacion.setCantidad(data.getListadoAlimentos().get(0).getCantidadAlimento());
        informacion.setCodigoAlimento(data.getListadoAlimentos().get(0).getInformacion().getCodigoAlimento());
        informacion.setNombreAlimento(data.getListadoAlimentos().get(0).getInformacion().getNombre());
        return informacion;
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
