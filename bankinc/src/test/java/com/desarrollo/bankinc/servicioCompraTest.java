package com.desarrollo.bankinc;

import com.desarrollo.bankinc.dto.controlCompra;
import com.desarrollo.bankinc.entidades.controlSaldos;
import com.desarrollo.bankinc.entidades.controlTransacciones;
import com.desarrollo.bankinc.entidades.infoTarjetas;
import com.desarrollo.bankinc.repositorios.repositorioCSaldos;
import com.desarrollo.bankinc.repositorios.repositorioCTransaccion;
import com.desarrollo.bankinc.repositorios.repositorioinfoTarjetas;
import com.desarrollo.bankinc.servicios.servicioCompra;
import com.desarrollo.bankinc.utilidades.utilidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class servicioCompraTest {
    @Mock
    private repositorioCTransaccion mockCTransaccion;

    @Mock
    private repositorioCSaldos mockCSaldos;

    @Mock
    private repositorioinfoTarjetas mockRtarjetas;

    @Mock
    private controlTransacciones controlTransacciones;

    @Mock
    private utilidad mockUtilidad;

    @InjectMocks
    private servicioCompra servicio;

    private infoTarjetas mockTarjetas;
    private controlSaldos mockSaldos;
    private controlTransacciones mockTransacciones;

    private controlCompra compraControl;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockTarjetas = new infoTarjetas();
        mockSaldos = new controlSaldos();
        mockTransacciones = new controlTransacciones();
        compraControl = new controlCompra();
    }

    @Test
    public void testGeneracionCompra() {
        mockTarjetas.setId(1L);
        mockTarjetas.setIndActivo(true);
        mockTarjetas.setIndbloqueo(false);
        mockTarjetas.setFechaTc("12/2025");
        mockTarjetas.setNumeroTcEnmascarada("1234********5678");
        when(mockRtarjetas.findByNumeroTc(anyString())).thenReturn(mockTarjetas);

        mockSaldos.setIdTc(mockTarjetas.getId());
        mockSaldos.setSaldoActual(500);
        when(mockCSaldos.findByIdTc(mockTarjetas.getId())).thenReturn(mockSaldos);

        when(mockUtilidad.verificacionVigencia(anyString())).thenReturn(true);

        compraControl = servicio.generacionCompra("1234567890123456", 100);

        assertTrue(compraControl.isConfirm());
        assertEquals(100, compraControl.getPrice());
        assertEquals("1234********5678", compraControl.getCardId());
        verify(mockCTransaccion, times(1)).save(any(controlTransacciones.class));
    }

    @Test
    public void testConsultaCompra() {
        // Configuración del mock de repositorioCTransaccion
        mockTransacciones.setIdtc(1L);
        mockTransacciones.setValorcompra(100);
        mockTransacciones.setFechacompra(new Date());
        when(mockCTransaccion.findById(anyLong())).thenReturn(Optional.of(mockTransacciones));

        // Configuración del mock de repositorioinfoTarjetas
        infoTarjetas mockTarjetas = new infoTarjetas();
        mockTarjetas.setNumeroTcEnmascarada("1234********5678");
        when(mockRtarjetas.findByIdTc(mockTransacciones.getIdtc())).thenReturn(mockTarjetas);

        // Simular llamada al método
        compraControl = servicio.consultaCompra(1L);

        // Verificaciones
        assertTrue(compraControl.isConfirm());
        assertEquals(100, compraControl.getPrice());
        assertEquals("1234********5678", compraControl.getCardId());
    }

    @Test
    public void testAnulacionTransaccion() {
        // Configuración del mock de repositorioCTransaccion
        mockTransacciones.setIdtc(1L);
        mockTransacciones.setValorcompra(100);
        mockTransacciones.setFechacompra(Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        mockTransacciones.setHoraCompra(LocalTime.now());
        when(mockCTransaccion.findByIdTs(anyLong())).thenReturn(mockTransacciones);

        // Configuración del mock de repositorioinfoTarjetas
        mockTarjetas.setId(1L);
        when(mockRtarjetas.findByNumeroTc(anyString())).thenReturn(mockTarjetas);

        // Configuración del mock de repositorioCSaldos
        mockSaldos.setIdTc(mockTarjetas.getId());
        mockSaldos.setSaldoActual(500);
        when(mockCSaldos.findByIdTc(mockTarjetas.getId())).thenReturn(mockSaldos);

        // Configuración del mock de utilidad
        when(mockUtilidad.verificacionTiempo(any(LocalDate.class), any(LocalTime.class))).thenReturn(true);

        // Simular llamada al método
        boolean result = servicio.anulacionTransaccion("1234567890123456", "1");

        // Verificaciones
        assertTrue(result);
        verify(mockCTransaccion, times(1)).save(any(controlTransacciones.class));
        verify(mockCSaldos, times(1)).save(any(controlSaldos.class));
    }
}
