package com.desarrollo.bankinc.servicios;

import com.desarrollo.bankinc.dto.controlSaldosTc;
import com.desarrollo.bankinc.entidades.controlSaldos;
import com.desarrollo.bankinc.entidades.infoTarjetas;
import com.desarrollo.bankinc.repositorios.repositorioCSaldos;
import com.desarrollo.bankinc.repositorios.repositorioinfoTarjetas;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class servicioSaldos {

    infoTarjetas tarjetas = new infoTarjetas();
    controlSaldos saldos = new controlSaldos();
    controlSaldosTc saldosTc = new controlSaldosTc();

    @Autowired
    repositorioinfoTarjetas rtarjetas;

    @Autowired
    repositorioCSaldos cSaldos;

    //Metodo para la creacion inicial de los saldos
    public void generacionSaldoTc(String cardId){
        tarjetas = rtarjetas.findByNumeroTc(cardId);
        int saldoFinal = 0;

        saldos.setIdTc(tarjetas.getId());
        saldos.setSaldoAnterior(0);
        saldos.setSaldoActual(0);
        saldos.setValorUltimaRecarga(0);
        saldoFinal = saldos.getSaldoActual();
        cSaldos.save(saldos);

        saldosTc.setBalance(saldoFinal);

    }

    //Metodo para recargar la TC
    public controlSaldosTc recargaTc(String cardId,int balance){

        tarjetas = rtarjetas.findByNumeroTc(cardId);

        int saldoFinal = 0;
        if (tarjetas.getIndActivo()){
            saldos = cSaldos.findByIdTc(tarjetas.getId());
            if (saldos != null){
                saldos.setIdTc(tarjetas.getId());
                saldos.setSaldoAnterior(saldos.getSaldoActual());
                saldos.setSaldoActual(saldos.getSaldoActual() + balance);
                saldos.setValorUltimaRecarga(balance);
                saldoFinal = saldos.getSaldoActual();
                cSaldos.save(saldos);
            }
        }else{
            saldosTc.setMessageError("La tarjeta debe estar activa");
        }

        saldosTc.setBalance(saldoFinal);

        return saldosTc;
    }

    //Metodo para la consulta del saldo
    public controlSaldosTc consultaSaldo(String cardId){
        tarjetas = rtarjetas.findByNumeroTc(cardId);
        saldos = cSaldos.findByIdTc(tarjetas.getId());

        saldosTc.setBalance(saldos.getSaldoActual());

        return saldosTc;
    }
}
