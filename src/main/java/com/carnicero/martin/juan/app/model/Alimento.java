package com.carnicero.martin.juan.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_informacion_nutricional")
    private InformacionNutricionalAlimento informacion;
    @Column(name = "cantidad_alimento")
    private double cantidadAlimento;
    @JsonManagedReference
    @JsonIgnore
    @ManyToMany(mappedBy = "listadoAlimentos", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Comida> comidas;

    public Alimento() {
        this.comidas = new ArrayList<>();
    }
}
