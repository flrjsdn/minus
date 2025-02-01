package com.hexa.muinus.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class UseCouponResponseDto {
    private String qrCode;// Base64 인코딩된 QR 코드 이미지 데이터
}
