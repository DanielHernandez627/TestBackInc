package com.desarrollo.bankinc.dto;

import lombok.*;


@NoArgsConstructor
@Setter
@Getter
public class responseCreacionTc {

    private int status;
    private String message;
    private String cardId;
}
