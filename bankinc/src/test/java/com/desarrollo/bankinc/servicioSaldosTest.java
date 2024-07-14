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
        mockTarjetas.setId(1L);
        when(Rtarjetas.findByNumeroTc(anyString())).thenReturn(mockTarjetas);

        servicio.generacionSaldoTc("1234567890123456");

        verify(Rsaldos, times(1)).save(any(controlSaldos.class));
    }

    @Test
    public void testRecargaTc() {
        mockTarjetas.setId(1L);
        mockTarjetas.setIndActivo(true);
        when(Rtarjetas.findByNumeroTc(anyString())).thenReturn(mockTarjetas);

        mockSaldos.setIdTc(mockTarjetas.getId());
        mockSaldos.setSaldoActual(0);
        when(Rsaldos.findByIdTc(mockTarjetas.getId())).thenReturn(mockSaldos);

        controlSaldosTc result = servicio.recargaTc("1234567890123456", 100);

        assertEquals(100, result.getBalance());
        verify(Rsaldos, times(1)).save(any(controlSaldos.class));
    }

    @Test
    public void testConsultaSaldo() {
        mockTarjetas.setId(1L);
        when(Rtarjetas.findByNumeroTc(anyString())).thenReturn(mockTarjetas);

        mockSaldos.setSaldoActual(200);
        when(Rsaldos.findByIdTc(mockTarjetas.getId())).thenReturn(mockSaldos);

        controlSaldosTc result = servicio.consultaSaldo("1234567890123456");

        assertEquals(200, result.getBalance());
    }
}
