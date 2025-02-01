package com.hexa.muinus.store.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponRequestDto {

    @NotNull(message = "가게 이름은 필수 입력 항목입니다.")
    private String storeName;

    @NotNull(message = "쿠폰 ID는 필수 입력 항목입니다.")
    private Integer couponId;

    @NotNull(message = "쿠폰 수량은 필수 입력 항목입니다.")
    @Min(value = 1, message = "쿠폰 수량은 최소 1개 이상이어야 합니다.")
    private Integer count;

    @NotNull(message = "유효기간은 필수 입력 항목입니다.")
    private LocalDateTime expirationDate;

    private LocalDateTime createAt;
}
