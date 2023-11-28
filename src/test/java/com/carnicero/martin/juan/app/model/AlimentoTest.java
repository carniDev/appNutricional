package com.carnicero.martin.juan.app.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlimentoTest {

    private Alimento alimento;

    @BeforeEach
    public void setUp() {
        alimento = new Alimento();
    }

    @Test
    public void testConstructor() {
        assertNotNull(alimento);
        assertNotNull(alimento.getComidas());
        assertEquals(0, alimento.getComidas().size());
    }

    @Test
    public void testIdAlimentoGetterSetter() {
        Long id = 1L;
        alimento.setIdAlimento(id);
        assertEquals(id, alimento.getIdAlimento());
    }

    @Test
    public void testInformacionNutricionalGetterSetter() {
        InformacionNutricionalAlimento informacion = Mockito.mock(InformacionNutricionalAlimento.class);
        alimento.setInformacion(informacion);
        assertEquals(informacion, alimento.getInformacion());
    }

    @Test
    public void testCantidadAlimentoGetterSetter() {
        double cantidad = 10.5;
        alimento.setCantidadAlimento(cantidad);
        assertEquals(cantidad, alimento.getCantidadAlimento(), 0.001); // Utilizamos un delta para comparar valores de punto flotante
    }

    @Test
    public void testComidasGetterSetter() {
        List<Comida> comidas = new ArrayList<>();
        Comida comida1 = Mockito.mock(Comida.class);
        Comida comida2 = Mockito.mock(Comida.class);
        comidas.add(comida1);
        comidas.add(comida2);

        alimento.setComidas(comidas);
        assertEquals(comidas, alimento.getComidas());
    }
}

