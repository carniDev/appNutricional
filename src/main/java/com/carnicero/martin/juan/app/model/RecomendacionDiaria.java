package com.carnicero.martin.juan.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "recomendacion-diaria")
@Getter
@Setter
@NoArgsConstructor
public class RecomendacionDiaria {

    //para probar

    private static double KCAL_DIARIAS= 2000;

    //Asignar en negativo, si al final del dia acaba en positivo el usuario se habra excedido
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recomendacion_diaria")
    private Long idRecomendacionDiaria;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.DETACH})
    @JoinColumn(name = "id_usuario")
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

    public RecomendacionDiaria(Usuario usuario) {
        this.usuario = usuario;
        this.fecha = LocalDate.now();
        this.kcalDiarias= 0;
        this.hidratosCarbonoDiarios= 0;
        this.proteinaDiaria= 0;
        this.grasaDiaria=0;
    }
}
