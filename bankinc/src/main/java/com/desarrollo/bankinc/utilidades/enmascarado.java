package com.desarrollo.bankinc.utilidades;

public class enmascarado {

    public String enmascararNumero(String numero) {

        if (numero.length() != 16) {
            throw new IllegalArgumentException("El número debe tener exactamente 16 dígitos");
        }

        String primeros4 = numero.substring(0, 4);
        String ultimos4 = numero.substring(12);

        StringBuilder enmascarado = new StringBuilder(primeros4);
        for (int i = 0; i < 8; i++) {
            enmascarado.append('*');
        }
        enmascarado.append(ultimos4);

        return enmascarado.toString();
    }

}
