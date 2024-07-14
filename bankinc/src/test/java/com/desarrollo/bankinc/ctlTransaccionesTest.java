package com.desarrollo.bankinc;

import com.desarrollo.bankinc.controlador.ctlTransacciones;
import com.desarrollo.bankinc.dto.*;
import com.desarrollo.bankinc.servicios.servicioCompra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ctlTransaccionesTest {

    @Mock
    private servicioCompra compraServicio;

    @InjectMocks
    private ctlTransacciones transaccionesCtl;

    private bodyPurchase purchaseBody;
    private controlCompra compraControl;

    private bodyAnulacion anulacionBody;

    @BeforeEach
    public void setUp() {

        Date fechaActual = new Date();

        purchaseBody = new bodyPurchase();

        purchaseBody.setCardId("1234567812345678");
        purchaseBody.setPrice(1000);

        compraControl = new controlCompra();

        compraControl.setCardId("1234567812345678");
        compraControl.setPrice(1000);
        compraControl.setId_compra(1L);
        compraControl.setFecha_compra(fechaActual);

        anulacionBody = new bodyAnulacion();

        anulacionBody.setCardId("1234567812345678");
        anulacionBody.setTransactionId(String.valueOf(1L));
    }

    @Test
    public void testPurchase_Success() {
        compraControl.setConfirm(true);
        when(compraServicio.generacionCompra(purchaseBody.getCardId(), purchaseBody.getPrice()))
                .thenReturn(compraControl);

        ResponseEntity<responseGeneracionCompraTc> response = transaccionesCtl.purchase(purchaseBody);

        assertEquals(200, response.getBody().getStatus());
        assertEquals("Exito", response.getBody().getMessage());
        assertEquals(purchaseBody.getCardId(), response.getBody().getCardId());
        assertEquals(purchaseBody.getPrice(), response.getBody().getPrice());
        assertEquals(compraControl.getId_compra(), response.getBody().getId_compra());
        assertEquals(compraControl.getFecha_compra(), response.getBody().getFecha_compra());
    }

    @Test
    public void testPurchase_Failure() {
        compraControl.setConfirm(false);
        compraControl.setMessageError("Compra rechazada");
        when(compraServicio.generacionCompra(purchaseBody.getCardId(), purchaseBody.getPrice()))
                .thenReturn(compraControl);

        ResponseEntity<responseGeneracionCompraTc> response = transaccionesCtl.purchase(purchaseBody);

        assertEquals(400, Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("Compra rechazada", response.getBody().getMessage());
    }

    @Test
    public void testSearchTransaction_Success() {
        compraControl.setConfirm(true);
        when(compraServicio.consultaCompra(1L)).thenReturn(compraControl);

        ResponseEntity<responseGeneracionCompraTc> response = transaccionesCtl.searchTransaction(1L);

        assertEquals(200, Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("Exito", response.getBody().getMessage());
        assertEquals(compraControl.getCardId(), response.getBody().getCardId());
        assertEquals(compraControl.getPrice(), response.getBody().getPrice());
        assertEquals(compraControl.getId_compra(), response.getBody().getId_compra());
        assertEquals(compraControl.getFecha_compra(), response.getBody().getFecha_compra());
    }

    @Test
    public void testSearchTransaction_Failure() {
        compraControl.setConfirm(false);
        compraControl.setMessageError("Transacción no encontrada");
        when(compraServicio.consultaCompra(1L)).thenReturn(compraControl);

        ResponseEntity<responseGeneracionCompraTc> response = transaccionesCtl.searchTransaction(1L);

        assertEquals(400, Objects.requireNonNull(response.getBody()).getStatus());
        assertNotNull(response.getBody().getMessage());
    }

    @Test
    public void testAnnulmentTransaction_Success() {
        when(compraServicio.anulacionTransaccion(anulacionBody.getCardId(), anulacionBody.getTransactionId()))
                .thenReturn(true);

        ResponseEntity<responseAnulacionTs> response = transaccionesCtl.annulmentTransaction(anulacionBody);

        assertEquals(200, response.getBody().getStatus());
        assertEquals("Exito", response.getBody().getMessage());
    }

    @Test
    public void testAnnulmentTransaction_Failure() {
        when(compraServicio.anulacionTransaccion(anulacionBody.getCardId(), anulacionBody.getTransactionId()))
                .thenReturn(false);

        ResponseEntity<responseAnulacionTs> response = transaccionesCtl.annulmentTransaction(anulacionBody);

        assertEquals(400, response.getBody().getStatus());
        assertEquals("Fallo", response.getBody().getMessage());
    }

}
