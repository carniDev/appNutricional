package com.carnicero.martin.juan.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comidas")
@Getter
@Setter
public class Comida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comida")
    private Long idComida;
    private TipoComida tipoComida;
    private LocalDateTime fechaComida;
    private double hidratosPorComida;
    private double proteinaPorComida;
    private double grasaPorComida;
    @OneToMany(mappedBy = "comida", orphanRemoval = true)
    private List<Alimento> informacionNutricionalAlimentos;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;


}
