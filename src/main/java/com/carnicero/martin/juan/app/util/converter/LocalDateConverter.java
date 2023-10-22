package com.carnicero.martin.juan.app.util.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter {


    public static LocalDate stringToLocalDateConverter(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(fecha, formatter);

    }
}
