package com.desarrollo.bankinc.utilidades;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class enmascarado {

    //Metodo para enmascarar el numero de la TC
    public String enmascararNumero(String numero) {

        if (numero.length() != 16) {
            throw new IllegalArgumentException("El número debe tener exactamente 16 dígitos");
        }

        String primeros4 = numero.substring(0, 4);
        String ultimos4 = numero.substring(12);

        StringBuilder enmascarado = new StringBuilder(primeros4);
        for (int i = 0; i < 8; i++) {
            enmascarado.append('*');
        }
        enmascarado.append(ultimos4);

        return enmascarado.toString();
    }

    //Metodo para verificar que el año de vencimiento de la tarjeta este vigente
    public boolean verificacionVigencia(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
            YearMonth yearMonth = YearMonth.parse(dateStr, formatter);
            int year = yearMonth.getYear();
            int currentYear = YearMonth.now().getYear();

            return year > currentYear;
        } catch (DateTimeParseException e) {
            System.err.println("Fecha no válida: " + dateStr);
            return false;
        }
    }
}
