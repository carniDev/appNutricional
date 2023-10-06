package com.carnicero.martin.juan.app.model;

import jakarta.persistence.*;

public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlimento;
    private String nombre;
    private double kcal;
    private double hidratosCarbono;
    private double azucares;
    private double proteinas;
    private double grasas;
    private double grasasSaturadas;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idComida")
    private Comida comida;


}
