package com.hexa.muinus.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveCouponResponseDto {
    private String storeName;
    private String couponName;
    private String content;
    private int discountRate;
    private LocalDateTime expirationDate;
    private LocalDateTime usedAt;
}
