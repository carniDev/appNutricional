package com.carnicero.martin.juan.app.request;

import com.carnicero.martin.juan.app.model.Alimento;
import com.carnicero.martin.juan.app.model.TipoComida;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RegistrarComida {
    @NotNull(message = "El tipo de comida debe de estar asignado")
    private TipoComida tipoComida;
    @Size(min = 1, message = "Debe contener al menos un alimento")
    private List<Alimento> listadoAlimentos;
    @NotEmpty(message = "No puede estar vacia")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String fechaComida;

}
