package com.carnicero.martin.juan.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "fecha_comida")
    private LocalDate fechaComida;
    @Column(name = "kcal")
    private int kcal;
    @Column(name = "hidratos_carbono")
    private int hidratosCarbono;
    @Column(name = "proteinas")
    private int proteinas;
    @Column(name = "grasas")
    private int grasas;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Alimento> listadoAlimentos;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;


    public Comida() {
        this.listadoAlimentos = new ArrayList<>();
    }
}
