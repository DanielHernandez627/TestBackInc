package com.desarrollo.bankinc.controlador;

import com.desarrollo.bankinc.servicios.servicioTarjetas;
import com.desarrollo.bankinc.dto.responseCreacionTc;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@NoArgsConstructor
public class ctlTarjetas {

    @Autowired
    servicioTarjetas starjetas;

    @GetMapping("/card/{productId}/number")
    public ResponseEntity<responseCreacionTc> getCardNumber(@PathVariable String productId) {

        responseCreacionTc creacionTc = new responseCreacionTc();

        String numero_tc = starjetas.generarNumeroTC(productId);

        if (numero_tc != null){
            creacionTc.setStatus(200);
            creacionTc.setMessage("Exito");
            creacionTc.setCardId(numero_tc);
        }else {
            creacionTc.setStatus(400);
            creacionTc.setMessage("Fallo");
        }
        return ResponseEntity.status(200).body(creacionTc);
    }

}
