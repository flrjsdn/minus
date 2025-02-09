package com.hexa.muinus.store.dto.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponListResponseDto {
    private Integer couponId;
    private String name;
    private Integer discountRate;
    private String content;
}
