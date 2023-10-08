package com.carnicero.martin.juan.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "alimentos")
@Getter
@Setter
public class Alimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alimento")
    private Long idAlimento;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_informacion_nutricional")
    private InformacionNutricionalAlimento informacion;
    @Column(name = "cantidad_alimento")
    private double cantidadAlimento;
    @Column(name = "hidrato_carbono")
    private double hidratosCarbono;
    private double proteina;
    private double grasa;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comida")
    private Comida comida;


}
