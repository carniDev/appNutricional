package com.carnicero.martin.juan.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Enumerated(EnumType.STRING)
    private TipoComida tipoComida;
    private LocalDateTime fechaComida;
    @ManyToMany
    @JoinTable(name = "comidas_alimentos",joinColumns = @JoinColumn(name = "id_comida"), inverseJoinColumns = @JoinColumn(name = "id_alimentos"))
    private List<Alimento> listadoAlimentos;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recomendacion_diaria")
    private RecomendacionDiaria recomendacionDiaria;


    public Comida() {
        this.listadoAlimentos = new ArrayList<>();
    }
}
