package com.carnicero.martin.juan.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "recomendacion-diaria")
@Getter
@Setter
public class RecomendacionDiaria {

    //Asignar en negativo, si al final del dia acaba en positivo el usuario se habra excedido
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recomendacion_diaria")
    private Long idRecomendacionDiaria;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Usuario usuario;
    @Column(name = "kcal_diarias")
    private double kcalDiarias;
    @Column(name = "hidratos_carbono_diarios")
    private double hidratosCarbonoDiarios;
    @Column(name = "proteina_diaria")
    private double proteinaDiaria;
    @Column(name = "grasa_diaria")
    private double grasaDiaria;
    private LocalDate fecha;
    @OneToMany(mappedBy = "recomendacionDiaria",orphanRemoval = true)
    private List<Comida>comidasUsuario;



}
