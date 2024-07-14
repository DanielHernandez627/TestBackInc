package com.desarrollo.bankinc.controlador;

import com.desarrollo.bankinc.dto.bodyBalance;
import com.desarrollo.bankinc.dto.controlSaldosTc;
import com.desarrollo.bankinc.dto.responseSaldosTc;
import com.desarrollo.bankinc.servicios.servicioSaldos;
import jakarta.persistence.Convert;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ctlSaldos {

    @Autowired
    servicioSaldos Ssaldos;

    @PostMapping("/card/balance")
    public ResponseEntity<responseSaldosTc> updateBalance(@RequestBody bodyBalance bodyBalance){

        responseSaldosTc saldosTc = new responseSaldosTc();
        controlSaldosTc cSaldosTc;

        cSaldosTc = Ssaldos.recargaTc(bodyBalance.getCardId(), Integer.parseInt(bodyBalance.getBalance()));

        if (cSaldosTc.getBalance() > 0){
            saldosTc.setStatus(200);
            saldosTc.setMessage("Exito");
            saldosTc.setCardId(bodyBalance.getCardId());
            saldosTc.setBalance(String.valueOf(cSaldosTc.getBalance()));
        }else{
            saldosTc.setStatus(400);
            saldosTc.setMessage(cSaldosTc.getMessageError());
            saldosTc.setCardId(bodyBalance.getCardId());
        }

        return ResponseEntity.status(200).body(saldosTc);
    }

    @GetMapping("/card/balance/{cardId}")
    public ResponseEntity<responseSaldosTc> balance(@PathVariable String cardId){

        controlSaldosTc cSaldosTc;
        responseSaldosTc saldosTc = new responseSaldosTc();
        cSaldosTc = Ssaldos.consultaSaldo(cardId);

        if (cSaldosTc != null){
            saldosTc.setStatus(200);
            saldosTc.setMessage("Exito");
            saldosTc.setCardId(cardId);
            saldosTc.setBalance(String.valueOf(cSaldosTc.getBalance()));
        }else{
            saldosTc.setStatus(400);
            saldosTc.setMessage("Error");
            saldosTc.setCardId(cardId);
        }

        return ResponseEntity.status(200).body(saldosTc);
    }

}
