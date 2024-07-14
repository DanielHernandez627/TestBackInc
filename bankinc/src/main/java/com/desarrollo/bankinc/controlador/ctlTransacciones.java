package com.desarrollo.bankinc.controlador;

import com.desarrollo.bankinc.dto.*;
import com.desarrollo.bankinc.servicios.servicioCompra;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class ctlTransacciones {

    @Autowired
    servicioCompra sCompra;

    @PostMapping("/transaction/purchase")
    public ResponseEntity<responseGeneracionCompraTc> enrollCard(@RequestBody bodyPurchase bodyPurchase){

        controlCompra cCompra;

        responseGeneracionCompraTc generacionCompra = new responseGeneracionCompraTc();

        cCompra = sCompra.generacionCompra(bodyPurchase.getCardId(),bodyPurchase.getPrice());

        if (cCompra.isConfirm()){
            generacionCompra.setStatus(200);
            generacionCompra.setMessage("Exito");
            generacionCompra.setPrice(cCompra.getPrice());
            generacionCompra.setCardId(cCompra.getCardId());
            generacionCompra.setId_compra(cCompra.getId_compra());
        }else{
            generacionCompra.setStatus(400);
            generacionCompra.setMessage(cCompra.getMessageError());
        }

        return ResponseEntity.status(200).body(generacionCompra);
    }

}
