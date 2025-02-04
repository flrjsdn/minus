package com.hexa.muinus.users.dto;

import com.hexa.muinus.common.enums.YesNo;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class StoreOwnerRegisterRequestDto {

    private final String userName;
    private final String userEmail;
    private final String userTelephone;
    private final String userType;
    private final String userBirth;
    private final Integer userPoint;
    private final BigDecimal locationX;
    private final BigDecimal locationY;
    private final String storeName;
    private final String storeAddress;
    private final String storeImageUrl;
    private final String phone;
    private final String storePhoneNumber;
    private final String fliMarketImageUrl;
    private final String registrationNumber;
    private final YesNo isFliMarketAllowed;
    private final Integer fliMarketSectionCount;
}
