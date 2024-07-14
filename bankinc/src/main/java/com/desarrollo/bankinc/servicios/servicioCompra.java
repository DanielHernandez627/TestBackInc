package com.desarrollo.bankinc.servicios;

import com.desarrollo.bankinc.dto.controlCompra;
import com.desarrollo.bankinc.entidades.controlSaldos;
import com.desarrollo.bankinc.entidades.controlTransacciones;
import com.desarrollo.bankinc.entidades.infoTarjetas;
import com.desarrollo.bankinc.repositorios.repositorioCSaldos;
import com.desarrollo.bankinc.repositorios.repositorioCTransaccion;
import com.desarrollo.bankinc.repositorios.repositorioinfoTarjetas;
import com.desarrollo.bankinc.utilidades.utilidad;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
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
    com.desarrollo.bankinc.utilidades.utilidad utilidad = new utilidad();
    controlCompra cCompra = new controlCompra();

    //Metodo para generacion de compras
    public controlCompra generacionCompra(String cardId,int price){
        tarjetas = rtarjetas.findByNumeroTc(cardId);
        saldos = csaldos.findByIdTc(tarjetas.getId());

        Date fechaActual = new Date();
        LocalTime currentTime = LocalTime.now();

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
                transacciones.setIndAnulado(false);
                transacciones.setHoraCompra(currentTime);
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

    //Metodo para consulta de compra
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

    //Metodo para anular transaccion
    public boolean anulacionTransaccion(String cardId,String transactionId){

        boolean state = false;

        transacciones = cTransaccion.findByIdTs(Long.valueOf(transactionId));

        LocalDate fechaCompra = transacciones.getFechacompra().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        if (utilidad.verificacionTiempo(fechaCompra,transacciones.getHoraCompra())){
            //Anulacion de transaccion
            transacciones.setIndAnulado(true);
            cTransaccion.save(transacciones);

            //Actualizacion de saldo
            tarjetas = rtarjetas.findByNumeroTc(cardId);
            saldos = csaldos.findByIdTc(tarjetas.getId());
            saldos.setSaldoAnterior(saldos.getSaldoActual());
            saldos.setSaldoActual(saldos.getSaldoActual() + transacciones.getValorcompra());
            csaldos.save(saldos);
            state = true;
        }

        return state;
    }
}
