package com.hexa.muinus.coupon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyDisCountRequestDto {
    private String qrCodeData;// QR 코드에 포함된 데이터
    private Integer totalAmount;// 원래 결제 금액


}
