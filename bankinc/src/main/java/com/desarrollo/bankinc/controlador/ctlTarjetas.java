package com.desarrollo.bankinc.controlador;

import com.desarrollo.bankinc.dto.bodyEnroll;
import com.desarrollo.bankinc.servicios.servicioSaldos;
import com.desarrollo.bankinc.servicios.servicioTarjetas;
import com.desarrollo.bankinc.dto.responseCreacionTc;
import com.desarrollo.bankinc.dto.responseActivacionTc;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class ctlTarjetas {

    @Autowired
    servicioTarjetas starjetas;

    @Autowired
    servicioSaldos Ssaldos;

    @GetMapping("/card/{productId}/number")
    public ResponseEntity<responseCreacionTc> getCardNumber(@PathVariable String productId) {

        responseCreacionTc creacionTc = new responseCreacionTc();

        String numero_tc = starjetas.generarNumeroTC(productId);

        if (numero_tc != null){
            Ssaldos.generacionSaldoTc(numero_tc);
            creacionTc.setStatus(200);
            creacionTc.setMessage("Exito");
            creacionTc.setCardId(numero_tc);
        }else {
            creacionTc.setStatus(400);
            creacionTc.setMessage("Fallo");
        }
        return ResponseEntity.status(200).body(creacionTc);
    }

    @PostMapping("/card/enroll")
    public ResponseEntity<responseActivacionTc> enrollCard(@RequestBody bodyEnroll bodyenroll){

        responseActivacionTc activacionTc = new responseActivacionTc();

        boolean respuesta = starjetas.activarTC(bodyenroll.getCardId());

        if (respuesta){
            activacionTc.setStatus(200);
            activacionTc.setMessage("Exito");
        }else{
            activacionTc.setStatus(400);
            activacionTc.setMessage("Fallo");
        }

        return ResponseEntity.status(200).body(activacionTc);
    }

    @DeleteMapping("/card/{cardId}")
    public ResponseEntity<responseActivacionTc> disableCard(@PathVariable String cardId){

        responseActivacionTc activacionTc = new responseActivacionTc();

        boolean respuesta = starjetas.inactivarTC(cardId);

        if (respuesta){
            activacionTc.setStatus(200);
            activacionTc.setMessage("Exito");
        }else{
            activacionTc.setStatus(400);
            activacionTc.setMessage("Fallo");
        }

        return ResponseEntity.status(200).body(activacionTc);
    }
}
