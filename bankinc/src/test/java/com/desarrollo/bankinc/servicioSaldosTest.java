package com.desarrollo.bankinc;

import com.desarrollo.bankinc.dto.controlSaldosTc;
import com.desarrollo.bankinc.entidades.controlSaldos;
import com.desarrollo.bankinc.entidades.infoTarjetas;
import com.desarrollo.bankinc.repositorios.repositorioCSaldos;
import com.desarrollo.bankinc.repositorios.repositorioinfoTarjetas;
import com.desarrollo.bankinc.servicios.servicioSaldos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class servicioSaldosTest {
    @Mock
    private repositorioinfoTarjetas Rtarjetas;

    @Mock
    private repositorioCSaldos Rsaldos;

    @InjectMocks
    private servicioSaldos servicio;

    private infoTarjetas mockTarjetas;
    private controlSaldos mockSaldos;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockTarjetas = new infoTarjetas();
        mockSaldos = new controlSaldos();
    }

    @Test
    public void testGeneracionSaldoTc() {
        // Configuración del mock de repositorioinfoTarjetas
        mockTarjetas.setId(1L); // Ejemplo de ID de tarjeta simulado
        when(Rtarjetas.findByNumeroTc(anyString())).thenReturn(mockTarjetas);

        // Simular llamada al método
        servicio.generacionSaldoTc("1234567890123456");

        // Verificar que se llame al método save del repositorioCSaldos
        verify(Rsaldos, times(1)).save(any(controlSaldos.class));
    }

    @Test
    public void testRecargaTc() {
        // Configuración del mock de repositorioinfoTarjetas
        mockTarjetas.setId(1L); // Ejemplo de ID de tarjeta simulado
        mockTarjetas.setIndActivo(true); // Tarjeta activa simulada
        when(Rtarjetas.findByNumeroTc(anyString())).thenReturn(mockTarjetas);

        // Configuración del mock de repositorioCSaldos
        mockSaldos.setIdTc(mockTarjetas.getId());
        when(Rsaldos.findByIdTc(mockTarjetas.getId())).thenReturn(mockSaldos);

        // Simular llamada al método
        controlSaldosTc result = servicio.recargaTc("1234567890123456", 100);

        // Verificaciones
        assertEquals(100, result.getBalance());
        verify(Rsaldos, times(1)).save(any(controlSaldos.class));
    }

    @Test
    public void testConsultaSaldo() {
        // Configuración del mock de repositorioinfoTarjetas
        mockTarjetas.setId(1L); // Ejemplo de ID de tarjeta simulado
        when(Rtarjetas.findByNumeroTc(anyString())).thenReturn(mockTarjetas);

        // Configuración del mock de repositorioCSaldos
        mockSaldos.setSaldoActual(200);
        when(Rsaldos.findByIdTc(mockTarjetas.getId())).thenReturn(mockSaldos);

        // Simular llamada al método
        controlSaldosTc result = servicio.consultaSaldo("1234567890123456");

        // Verificaciones
        assertEquals(200, result.getBalance());
    }
}
