package com.desarrollo.bankinc;

import com.desarrollo.bankinc.controlador.ctlTarjetas;
import com.desarrollo.bankinc.dto.responseActivacionTc;
import com.desarrollo.bankinc.dto.responseCreacionTc;
import com.desarrollo.bankinc.servicios.servicioSaldos;
import com.desarrollo.bankinc.servicios.servicioTarjetas;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.ResponseEntity;
import com.desarrollo.bankinc.dto.bodyEnroll;
import org.junit.jupiter.api.BeforeEach;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ctlTarjetasTest {

    @Mock
    private servicioTarjetas tarjetasServicio;

    @Mock
    private servicioSaldos saldosServicio;

    @InjectMocks
    private ctlTarjetas tarjetasCtl;

    private bodyEnroll bodyenroll;


    @BeforeEach
    public void setUp() {
        bodyenroll = new bodyEnroll();
        bodyenroll.setCardId("1234567812345678");
    }

    @Test
    public void testGetCardNumber_Success() {
        String productId = "156375";
        String numeroTc = "1234567812345678";
        when(tarjetasServicio.generarNumeroTC(productId)).thenReturn(numeroTc);

        ResponseEntity<responseCreacionTc> response = tarjetasCtl.getCardNumber(productId);

        verify(saldosServicio).generacionSaldoTc(numeroTc);
        assertEquals(200, Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("Exito", response.getBody().getMessage());
        assertEquals(numeroTc, response.getBody().getCardId());
    }

    @Test
    public void testGetCardNumber_Failure() {
        String productId = "123";
        when(tarjetasServicio.generarNumeroTC(productId)).thenReturn(null);

        ResponseEntity<responseCreacionTc> response = tarjetasCtl.getCardNumber(productId);

        assertEquals(400, Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("Fallo", response.getBody().getMessage());
    }


    @Test
    public void testEnrollCard_Success() {
        when(tarjetasServicio.activarTC(bodyenroll.getCardId())).thenReturn(true);

        ResponseEntity<responseActivacionTc> response = tarjetasCtl.enrollCard(bodyenroll);

        assertEquals(200, Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("Exito", response.getBody().getMessage());
    }

    @Test
    public void testEnrollCard_Failure() {
        when(tarjetasServicio.activarTC(bodyenroll.getCardId())).thenReturn(false);

        ResponseEntity<responseActivacionTc> response = tarjetasCtl.enrollCard(bodyenroll);

        assertEquals(400, Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("Fallo", response.getBody().getMessage());
    }

    @Test
    public void testDisableCard_Success() {
        String cardId = "1234567812345678";
        when(tarjetasServicio.inactivarTC(cardId)).thenReturn(true);

        ResponseEntity<responseActivacionTc> response = tarjetasCtl.disableCard(cardId);

        assertEquals(200, Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("Exito", response.getBody().getMessage());
    }

    @Test
    public void testDisableCard_Failure() {
        String cardId = "1234567812345678";
        when(tarjetasServicio.inactivarTC(cardId)).thenReturn(false);

        ResponseEntity<responseActivacionTc> response = tarjetasCtl.disableCard(cardId);

        assertEquals(400, Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("Fallo", response.getBody().getMessage());
    }

}
