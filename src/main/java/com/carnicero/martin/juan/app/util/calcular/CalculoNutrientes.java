package com.carnicero.martin.juan.app.util.calcular;

import com.carnicero.martin.juan.app.model.Comida;
import com.carnicero.martin.juan.app.response.MacroNutritientesComida;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CalculoNutrientes {
    public static MacroNutritientesComida calcular(Comida comida) {
        AtomicInteger hidratos = new AtomicInteger();
        AtomicInteger proteinas = new AtomicInteger();
        AtomicInteger grasas = new AtomicInteger();

            comida.getListadoAlimentos().forEach(alimento -> {
                double cantidad = alimento.getCantidadAlimento();
                hidratos.addAndGet((int) (cantidad * alimento.getInformacion().getHidratosCarbono()) / 100);
                proteinas.addAndGet((int) (cantidad * alimento.getInformacion().getProteinas()) / 100);
                grasas.addAndGet((int) (cantidad * alimento.getInformacion().getGrasas()) / 100);
            });

        MacroNutritientesComida nutrientesComida = new MacroNutritientesComida();
        nutrientesComida.setHidratosCarbono(hidratos.get());
        nutrientesComida.setProteinas(proteinas.get());
        nutrientesComida.setGrasas(grasas.get());
        nutrientesComida.setKcal(calculoKcal(nutrientesComida));
        return nutrientesComida;
    }
    public static MacroNutritientesComida calcular(List<Comida> comidas) {
        AtomicInteger hidratos = new AtomicInteger();
        AtomicInteger proteinas = new AtomicInteger();
        AtomicInteger grasas = new AtomicInteger();
        comidas.forEach(comida -> {
            comida.getListadoAlimentos().forEach(alimento -> {
                double cantidad = alimento.getCantidadAlimento();
                hidratos.addAndGet((int) (cantidad * alimento.getInformacion().getHidratosCarbono()) / 100);
                proteinas.addAndGet((int) (cantidad * alimento.getInformacion().getProteinas()) / 100);
                grasas.addAndGet((int) (cantidad * alimento.getInformacion().getGrasas()) / 100);
            });
        });

        MacroNutritientesComida nutrientesComida = new MacroNutritientesComida();
        nutrientesComida.setHidratosCarbono(hidratos.get());
        nutrientesComida.setProteinas(proteinas.get());
        nutrientesComida.setGrasas(grasas.get());
        nutrientesComida.setKcal(calculoKcal(nutrientesComida));
        return nutrientesComida;
    }



    private static int calculoKcal(MacroNutritientesComida nutrientes) {
        int kcal = 0;
        kcal += (nutrientes.getHidratosCarbono() * 4) + (nutrientes.getProteinas() * 4) + (nutrientes.getGrasas() * 9);
        return kcal;
    }

}
