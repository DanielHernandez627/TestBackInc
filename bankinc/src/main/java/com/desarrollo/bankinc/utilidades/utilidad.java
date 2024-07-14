package com.desarrollo.bankinc.utilidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class utilidad {

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

    //Metodo para verificacion de tiempo transcurrido para anulacion
    public boolean verificacionTiempo(LocalDate fechaEvento, LocalTime horaEvento) {
        try{
            LocalDateTime fechaHoraEvento = LocalDateTime.of(fechaEvento, horaEvento);
            LocalDateTime fechaHoraActual = LocalDateTime.now();
            long horasTranscurridas = ChronoUnit.HOURS.between(fechaHoraEvento, fechaHoraActual);
            return horasTranscurridas < 24;
        }catch (DateTimeParseException e){
            System.err.println("Hora no validad");
            return false;
        }

    }
}
