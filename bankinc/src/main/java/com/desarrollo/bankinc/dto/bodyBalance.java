package com.desarrollo.bankinc.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class bodyBalance {
    private String cardId;
    private String balance;
}
