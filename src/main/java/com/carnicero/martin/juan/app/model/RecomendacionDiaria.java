package com.carnicero.martin.juan.app.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "recomendacion-diaria")
public class RecomendacionDiaria {

    //Asignar en negativo, si al final del dia acaba en positivo el usuario se habra excedido
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recomendacion_diaria")
    private Long idRecomendacionDiaria;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Usuario usuario;
    private double hidratosCarbonoDiarios;
    private double proteinaDiaria;
    private LocalDate fecha;

}
