package com.desarrollo.bankinc.dto;

import lombok.*;


@NoArgsConstructor
@Setter
@Getter
public class responseSaldosTc {

    private int status;
    private String message;
    private String cardId;
    private String balance;
}
