package com.desarrollo.bankinc.controlador;

import com.desarrollo.bankinc.dto.*;
import com.desarrollo.bankinc.servicios.servicioCompra;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ctlTransacciones {

    @Autowired
    servicioCompra sCompra;

    @PostMapping("/transaction/purchase")
    public ResponseEntity<responseGeneracionCompraTc> purchase(@RequestBody bodyPurchase bodyPurchase){

        controlCompra cCompra;

        responseGeneracionCompraTc generacionCompra = new responseGeneracionCompraTc();

        cCompra = sCompra.generacionCompra(bodyPurchase.getCardId(),bodyPurchase.getPrice());

        return getResponseGeneracionCompraTcResponseEntity(cCompra, generacionCompra);
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<responseGeneracionCompraTc> searchTransaction(@PathVariable Long transactionId){

        controlCompra cCompra;

        responseGeneracionCompraTc generacionCompra = new responseGeneracionCompraTc();

        cCompra = sCompra.consultaCompra(transactionId);

        return getResponseGeneracionCompraTcResponseEntity(cCompra, generacionCompra);
    }

    @PostMapping("/transaction/anulation")
    public ResponseEntity<responseAnulacionTs> annulmentTransaction(@RequestBody bodyAnulacion bodyAnulacion){
        responseAnulacionTs anulacionTs = new responseAnulacionTs();
        boolean respuestaTransaccion = sCompra.anulacionTransaccion(bodyAnulacion.getCardId(), bodyAnulacion.getTransactionId());
        if (respuestaTransaccion){
            anulacionTs.setStatus(200);
            anulacionTs.setMessage("Exito");
        }else{
            anulacionTs.setStatus(400);
            anulacionTs.setMessage("Fallo");
        }

        return ResponseEntity.status(200).body(anulacionTs);
    }

    private ResponseEntity<responseGeneracionCompraTc> getResponseGeneracionCompraTcResponseEntity(controlCompra cCompra, responseGeneracionCompraTc generacionCompra) {
        if (cCompra.isConfirm()){
            generacionCompra.setStatus(200);
            generacionCompra.setMessage("Exito");
            generacionCompra.setPrice(cCompra.getPrice());
            generacionCompra.setCardId(cCompra.getCardId());
            generacionCompra.setId_compra(cCompra.getId_compra());
            generacionCompra.setFecha_compra(cCompra.getFecha_compra());
        }else{
            generacionCompra.setStatus(400);
            generacionCompra.setMessage(cCompra.getMessageError());
        }

        return ResponseEntity.status(200).body(generacionCompra);
    }
}
