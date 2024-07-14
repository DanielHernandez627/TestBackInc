package com.desarrollo.bankinc.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class bodyPurchase {
    private String cardId;
    private int price;
}
