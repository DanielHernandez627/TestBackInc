package com.desarrollo.bankinc.servicios;

import com.desarrollo.bankinc.entidades.infoTarjetas;
import com.desarrollo.bankinc.dto.generarTcDto;
import com.desarrollo.bankinc.repositorios.repositorioinfoTarjetas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class servicioTarjetas implements Serializable {

    infoTarjetas tarjetas;

    @Autowired
    repositorioinfoTarjetas rtarjetas;

    public boolean generarNumeroTC(generarTcDto tcDto){

        return false;
    }
}
