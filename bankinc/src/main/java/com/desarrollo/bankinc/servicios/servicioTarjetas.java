package com.desarrollo.bankinc.servicios;

import com.desarrollo.bankinc.entidades.infoTarjetas;
import com.desarrollo.bankinc.repositorios.repositorioinfoTarjetas;
import com.desarrollo.bankinc.repositorios.repositorioProductos;
import com.desarrollo.bankinc.utilidades.utilidad;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Random;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class servicioTarjetas implements Serializable {

    infoTarjetas tarjetas = new infoTarjetas();
    utilidad uenmascarado = new utilidad();
    private final Faker faker = new Faker();

    @Autowired
    repositorioinfoTarjetas rtarjetas;

    @Autowired
    repositorioProductos productos;

    public String generarNumeroTC(String productId){

        boolean state = false;

        //Generacion de numero de TC
        String numero_tc = null;
        String numero_tc_enmascarado = null;
        Random random = new Random();
        long number = (long) (random.nextDouble() * 1_000_000_0000L);
        numero_tc = productId + number;
        numero_tc_enmascarado = uenmascarado.enmascararNumero(numero_tc);

        //Generacion fecha vencimiento
        String fecha_vencimiento = null;
        int currentYear = LocalDate.now().getYear();
        int year = currentYear + 3;
        int month = random.nextInt(12) + 1;
        fecha_vencimiento =  String.format("%02d/%d", month, year);

        tarjetas.setNumeroTc(numero_tc);
        tarjetas.setFechaTc(fecha_vencimiento);
        tarjetas.setNombreTitular(faker.name().firstName());
        tarjetas.setApellidoTitular(faker.name().lastName());
        tarjetas.setIndActivo(false);
        tarjetas.setIdProducto(productos.findByCodigo(productId).getId());
        tarjetas.setNumeroTcEnmascarada(numero_tc_enmascarado);
        tarjetas.setIndbloqueo(false);

        rtarjetas.save(tarjetas);

        return numero_tc;
    }

    public boolean activarTC(String cardId){

        boolean state = false;

        tarjetas = rtarjetas.findByNumeroTc(cardId);

        if (tarjetas != null){
            tarjetas.setIndActivo(true);
            rtarjetas.save(tarjetas);
            state = true;
        }

        return state;
    }

    public boolean inactivarTC(String cardId){

        boolean state = false;

        tarjetas = rtarjetas.findByNumeroTc(cardId);

        if (tarjetas != null){
            tarjetas.setIndActivo(false);
            rtarjetas.save(tarjetas);
            state = true;
        }

        return state;
    }
}
