package com.desarrollo.bankinc.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class controlSaldosTc {
    private String messageError;
    private int balance;
}
