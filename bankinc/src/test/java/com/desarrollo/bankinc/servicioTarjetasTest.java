package com.desarrollo.bankinc;

import com.desarrollo.bankinc.entidades.Productos;
import com.desarrollo.bankinc.entidades.infoTarjetas;
import com.desarrollo.bankinc.repositorios.repositorioProductos;
import com.desarrollo.bankinc.repositorios.repositorioinfoTarjetas;
import com.desarrollo.bankinc.servicios.servicioTarjetas;
import com.desarrollo.bankinc.utilidades.utilidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class servicioTarjetasTest {

    @Mock
    private repositorioinfoTarjetas rtarjetas;

    @Mock
    private repositorioProductos productos;

    @Mock
    private utilidad uenmascarado;


    @InjectMocks
    private servicioTarjetas tarjetasServicio;

    private infoTarjetas tarjeta;
    private Productos pr;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tarjeta = new infoTarjetas();
        pr = new Productos();
    }

    @Test
    public void testGenerarNumeroTC() {
        String productId = "1234";
        tarjeta.setIdProducto(1);

        when(productos.findByCodigo(productId)).thenReturn(pr);
        when(rtarjetas.save(any(infoTarjetas.class))).thenReturn(tarjeta);
        when(uenmascarado.enmascararNumero(anyString())).thenReturn("1234********5678");

        String numeroTC = tarjetasServicio.generarNumeroTC(productId);

        assertNotNull(numeroTC);
        assertTrue(numeroTC.startsWith(productId));
        verify(rtarjetas, times(1)).save(any(infoTarjetas.class));
    }

    @Test
    public void testActivarTC() {
        String cardId = "1234567812345678";
        tarjeta.setNumeroTc(cardId);

        when(rtarjetas.findByNumeroTc(cardId)).thenReturn(tarjeta);
        when(rtarjetas.save(any(infoTarjetas.class))).thenReturn(tarjeta);

        boolean result = tarjetasServicio.activarTC(cardId);

        assertTrue(result);
        assertTrue(tarjeta.getIndActivo());
        verify(rtarjetas, times(1)).findByNumeroTc(cardId);
        verify(rtarjetas, times(1)).save(tarjeta);
    }

    @Test
    public void testInactivarTC() {
        String cardId = "1234567812345678";
        tarjeta.setNumeroTc(cardId);

        when(rtarjetas.findByNumeroTc(cardId)).thenReturn(tarjeta);
        when(rtarjetas.save(any(infoTarjetas.class))).thenReturn(tarjeta);

        boolean result = tarjetasServicio.inactivarTC(cardId);

        assertTrue(result);
        assertFalse(tarjeta.getIndActivo());
        verify(rtarjetas, times(1)).findByNumeroTc(cardId);
        verify(rtarjetas, times(1)).save(tarjeta);
    }

}
