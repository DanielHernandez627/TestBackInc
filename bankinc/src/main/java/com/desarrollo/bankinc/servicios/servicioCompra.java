package com.desarrollo.bankinc.servicios;

import com.desarrollo.bankinc.dto.controlCompra;
import com.desarrollo.bankinc.entidades.controlSaldos;
import com.desarrollo.bankinc.entidades.controlTransacciones;
import com.desarrollo.bankinc.entidades.infoTarjetas;
import com.desarrollo.bankinc.repositorios.repositorioCSaldos;
import com.desarrollo.bankinc.repositorios.repositorioCTransaccion;
import com.desarrollo.bankinc.repositorios.repositorioinfoTarjetas;
import com.desarrollo.bankinc.utilidades.enmascarado;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class servicioCompra {

    private final Faker faker = new Faker();

    @Autowired
    repositorioCTransaccion cTransaccion;

    @Autowired
    repositorioCSaldos csaldos;

    @Autowired
    repositorioinfoTarjetas rtarjetas;

    infoTarjetas tarjetas = new infoTarjetas();
    controlSaldos saldos = new controlSaldos();
    controlTransacciones transacciones = new controlTransacciones();
    enmascarado utilidad = new enmascarado();
    controlCompra cCompra = new controlCompra();

    public controlCompra generacionCompra(String cardId,int price){
        tarjetas = rtarjetas.findByNumeroTc(cardId);
        saldos = csaldos.findByIdTc(tarjetas.getId());

        Date fechaActual = new Date();

        //Verificacion de filtros para transaccion
        if(tarjetas.getIndActivo() && !tarjetas.getIndbloqueo() && utilidad.verificacionVigencia(tarjetas.getFechaTc()) && saldos.getSaldoActual() > 0){
            if (saldos.getSaldoActual() >= price){
                //Actualizacion de saldos
                saldos.setSaldoAnterior(saldos.getSaldoActual());
                saldos.setSaldoActual(saldos.getSaldoActual() - price);

                //Generacion de transaccion
                transacciones.setId(0);
                transacciones.setIdtc(tarjetas.getId());
                transacciones.setValorcompra(price);
                transacciones.setFechacompra(fechaActual);
                transacciones.setNombrecomercio(faker.company().name());
                cTransaccion.save(transacciones);
                cCompra.setConfirm(true);
                cCompra.setPrice(price);
                cCompra.setCardId(tarjetas.getNumeroTcEnmascarada());
                cCompra.setId_compra(cTransaccion.findMaxId());
                cCompra.setFecha_compra(transacciones.getFechacompra());
            }else{
                cCompra.setMessageError("Saldo insuficiente para la compra");
                cCompra.setConfirm(false);
            }
        }else{
            cCompra.setMessageError("No es posible realizar la compra");
            cCompra.setConfirm(false);
        }

        return cCompra;
    }

    public controlCompra consultaCompra(Long transactionId){
        Optional<controlTransacciones> respuesta = cTransaccion.findById(transactionId);
        tarjetas = rtarjetas.findByIdTc(respuesta.get().getIdtc());
        cCompra.setCardId(tarjetas.getNumeroTcEnmascarada());
        cCompra.setPrice(respuesta.get().getValorcompra());
        cCompra.setFecha_compra(respuesta.get().getFechacompra());
        cCompra.setId_compra(transactionId);
        cCompra.setConfirm(true);

        return cCompra;
    }

}
