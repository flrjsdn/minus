package com.hexa.muinus.store.domain.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponRequestDto {

    private String storeName;
    private Integer couponId;
    private Integer count;
    private LocalDateTime expirationDate;
    private LocalDateTime createAt;
}
