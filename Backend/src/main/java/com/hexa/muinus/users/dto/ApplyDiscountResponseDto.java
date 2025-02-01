package com.hexa.muinus.coupon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyDiscountResponseDto {
    private Integer discountAmount;// 할인된 금액
    private String message;// 할인 적용 메세지
}
