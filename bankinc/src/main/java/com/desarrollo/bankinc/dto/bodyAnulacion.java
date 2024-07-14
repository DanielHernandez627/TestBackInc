package com.desarrollo.bankinc.dto;

import lombok.*;


@NoArgsConstructor
@Setter
@Getter
public class bodyAnulacion {
    private String cardId;
    private String transactionId;
}
