package com.desarrollo.bankinc.dto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class responseGeneracionCompraTc {

    private int status;
    private String message;
    private int price;
    private String cardId;
    private Long id_compra;
    private Date fecha_compra;
}
