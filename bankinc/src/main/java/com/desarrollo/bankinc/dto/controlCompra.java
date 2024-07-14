package com.desarrollo.bankinc.dto;

import lombok.*;

import java.util.Date;


@NoArgsConstructor
@Setter
@Getter
public class controlCompra {
    private String messageError;
    private boolean confirm;
    private int price;
    private String cardId;
    private Long id_compra;
    private Date fecha_compra;
}
