package com.hexa.muinus.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplyDiscountResponseDto {
    private Integer discountAmount;// 할인된 금액
    private String message;// 할인 적용 메세지
}
