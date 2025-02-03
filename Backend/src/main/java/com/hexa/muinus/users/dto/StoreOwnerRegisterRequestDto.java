package com.hexa.muinus.users.dto;

import com.hexa.muinus.common.enums.YesNo;
import com.hexa.muinus.store.domain.store.Store;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.geo.Point;

@Getter
@Builder
public class StoreOwnerRegisterRequestDto {

    private final String userName;
    private final String userEmail;
    private final String userTelephone;
    private final String userType;
    private final Integer userPoint;
    private final Point location;
    private final String storeName;
    private final String storeAddress;
    private final String storePhoneNumber;
    private final String fliMarketImageUrl;
    private final String registrationNumber;
    private final YesNo isFliMarketAllowed;
    private final Byte fliMarketSectionCount;
}
