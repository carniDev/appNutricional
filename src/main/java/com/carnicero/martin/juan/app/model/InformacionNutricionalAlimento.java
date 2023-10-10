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
    private String nombre;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    private double kcal;
    @Column(name = "hidratos_carbono")
    private double hidratosCarbono;
    private double proteinas;
    private double grasas;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_alimento")
    private Alimento alimento;

}
