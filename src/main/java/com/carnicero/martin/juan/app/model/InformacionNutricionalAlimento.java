package com.carnicero.martin.juan.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "informacion_nutricional")
@Getter
@Setter
public class InformacionNutricionalAlimento {
    //el alimento por cada 100 gr
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_informacion_nutricional")
    private Long idInformacionNutricional;
    @Column(name = "codigo_alimento",unique = true)
    private String codigoAlimento;
    private String nombre;
    private int kcal;
    @Column(name = "hidratos_carbono")
    private int hidratosCarbono;
    private int proteinas;
    private int grasas;

}
