package com.carnicero.martin.juan.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    @ManyToMany(mappedBy = "listadoAlimentos")
    private List<Comida> comidas;

}
