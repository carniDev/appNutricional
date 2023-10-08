package com.carnicero.martin.juan.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "informacion_nutricional")
@Getter
@Setter
public class InformacionNutricionalAlimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_informacion_nutricional")
    private Long idInformacionNutricional;
    private String nombre;
    private double kcal;
    @Column(name = "hidratos_carbono")
    private double hidratosCarbono;
    private double azucares;
    private double proteinas;
    private double grasas;
    @Column(name = "grasas_saturadas")
    private double grasasSaturadas;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_alimento")
    private Alimento alimento;



}
