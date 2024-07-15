package com.desarrollo.bankinc;

import com.desarrollo.bankinc.utilidades.utilidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class utilidadesTest {

    private utilidad utilidades;

    @BeforeEach
    public void setUp() {
        utilidades = new utilidad();
    }

    @Test
    public void testEnmascararNumero() {
        String numero = "1234567812345678";
        String esperado = "1234********5678";
        String resultado = utilidades.enmascararNumero(numero);
        assertEquals(esperado, resultado);
    }

    @Test
    public void testEnmascararNumeroInvalidLength() {
        String numero = "12345678";
        assertThrows(IllegalArgumentException.class, () -> {
            utilidades.enmascararNumero(numero);
        });
    }

    @Test
    public void testVerificacionVigencia() {
        String fechaValida = "12/2025";
        String fechaInvalida = "12/2020";

        assertTrue(utilidades.verificacionVigencia(fechaValida));
        assertFalse(utilidades.verificacionVigencia(fechaInvalida));
    }

    @Test
    public void testVerificacionVigenciaFechaInvalida() {
        String fechaInvalida = "13/2025"; // Mes inválido
        assertFalse(utilidades.verificacionVigencia(fechaInvalida));
    }

    @Test
    public void testVerificacionTiempoMenosDe24Horas() {
        LocalDate fechaEvento = LocalDate.now().minusDays(1);
        LocalTime horaEvento = LocalTime.now().plusHours(1);

        assertTrue(utilidades.verificacionTiempo(fechaEvento, horaEvento));
    }

    @Test
    public void testVerificacionTiempoMasDe24Horas() {
        LocalDate fechaEvento = LocalDate.now().minusDays(1);
        LocalTime horaEvento = LocalTime.now().minusHours(24);

        assertFalse(utilidades.verificacionTiempo(fechaEvento, horaEvento));
    }

}
