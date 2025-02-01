package com.hexa.muinus.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class UseCouponResponseDto {
    private String barcode;// Base64 인코딩된 바코드 이미지 데이터
}
