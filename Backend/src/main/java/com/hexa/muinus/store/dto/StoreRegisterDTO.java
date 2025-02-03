package com.hexa.muinus.store.dto;

import com.hexa.muinus.common.enums.YesNo;
import com.hexa.muinus.common.validator.StoreFlimarketValidator;
import com.hexa.muinus.store.domain.store.Store;
import com.hexa.muinus.users.domain.user.Users;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@StoreFlimarketValidator
public class StoreRegisterDTO {

    @NotNull
    private Integer userNo;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal locationX;

    @NotNull
    private BigDecimal locationY;

    @NotNull
    private String address;

    private String storeImageUrl;

    @NotNull
    private String registrationNo;

    @NotNull
    private String phone;

    @NotNull
    private YesNo flimarketYn;

    private String flimarketImageUrl;

    private Integer flimarketSectionCnt;



    /**
     * Store Regist를 위한 DTO -> Entity 변환
     *
     * @param user 가게를 등록하는 유저 객체(점주)
     * @return Store Entity 객체
     * - storeNo 제외 : Auto Increment
     * - createdAt 제외 : Default
     */
    public Store toEntity(Users user) {
        return Store.builder()
                .user(user) // Users 주입
                .name(this.name)
                .locationX(locationX)
                .locationY(locationY)
                .address(this.address)
                .storeImageUrl(this.storeImageUrl)
                .registrationNo(this.registrationNo)
                .phone(this.phone)
                .flimarketYn(this.flimarketYn)
                .flimarketImageUrl(this.flimarketImageUrl)
                .flimarketSectionCnt(this.flimarketSectionCnt == null ? 0 : this.flimarketSectionCnt)
                .build();
    }

}
