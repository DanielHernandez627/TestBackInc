package com.desarrollo.bankinc;

import com.desarrollo.bankinc.controlador.ctlSaldos;
import com.desarrollo.bankinc.dto.bodyBalance;
import com.desarrollo.bankinc.dto.controlSaldosTc;
import com.desarrollo.bankinc.dto.responseSaldosTc;
import com.desarrollo.bankinc.servicios.servicioSaldos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ctlSaldosTest {

    @Mock
    private servicioSaldos Ssaldos;

    @InjectMocks
    private ctlSaldos saldosCtl;

    private bodyBalance balanceBody;
    private controlSaldosTc saldosControl;

    @BeforeEach
    public void setUp() {
        balanceBody = new bodyBalance();
        balanceBody.setCardId("1234567812345678");
        balanceBody.setBalance("1000");

        saldosControl = new controlSaldosTc();
    }

    @Test
    public void testUpdateBalance_Success() {
        saldosControl.setBalance(1000);
        when(Ssaldos.recargaTc(balanceBody.getCardId(), Integer.parseInt(balanceBody.getBalance())))
                .thenReturn(saldosControl);

        ResponseEntity<responseSaldosTc> response = saldosCtl.updateBalance(balanceBody);

        assertEquals(200, Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("Exito", response.getBody().getMessage());
        assertEquals(balanceBody.getCardId(), response.getBody().getCardId());
        assertEquals("1000", response.getBody().getBalance());
    }

    @Test
    public void testUpdateBalance_Failure() {
        saldosControl.setBalance(0);
        saldosControl.setMessageError("No se realizo la recarga");
        when(Ssaldos.recargaTc(balanceBody.getCardId(), Integer.parseInt(balanceBody.getBalance())))
                .thenReturn(saldosControl);

        ResponseEntity<responseSaldosTc> response = saldosCtl.updateBalance(balanceBody);

        assertEquals(400, Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("No se realizo la recarga", response.getBody().getMessage());
        assertEquals(balanceBody.getCardId(), response.getBody().getCardId());
    }

    @Test
    public void testBalance_Success() {
        saldosControl.setBalance(1000);
        when(Ssaldos.consultaSaldo(balanceBody.getCardId())).thenReturn(saldosControl);

        ResponseEntity<responseSaldosTc> response = saldosCtl.balance(balanceBody.getCardId());

        assertEquals(200, Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("Exito", response.getBody().getMessage());
        assertEquals(balanceBody.getCardId(), response.getBody().getCardId());
        assertEquals("1000", response.getBody().getBalance());
    }

    @Test
    public void testBalance_Failure() {
        when(Ssaldos.consultaSaldo(balanceBody.getCardId())).thenReturn(null);

        ResponseEntity<responseSaldosTc> response = saldosCtl.balance(balanceBody.getCardId());

        assertEquals(400, Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("Error", response.getBody().getMessage());
        assertEquals(balanceBody.getCardId(), response.getBody().getCardId());
    }

}
