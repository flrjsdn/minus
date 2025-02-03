package com.hexa.muinus.store.dto;

import com.hexa.muinus.common.enums.YesNo;
import com.hexa.muinus.common.validator.StoreFlimarketValidator;
import com.hexa.muinus.store.domain.store.Store;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@StoreFlimarketValidator
public class StoreModifyDTO {

    @NotNull
    private Integer storeNo;

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
    private YesNo flimarketYn; // Y 또는 N

    private String flimarketImageUrl;

    private Integer flimarketSectionCnt;

    public void updateEntity(Store store) {
        store.setName(this.name);
        store.setLocationX(this.locationX);
        store.setLocationY(this.locationY);
        store.setAddress(this.address);
        store.setStoreImageUrl(this.storeImageUrl);
        store.setRegistrationNo(this.registrationNo);
        store.setPhone(this.phone);
        store.setFlimarketYn(this.flimarketYn);
        store.setFlimarketImageUrl(this.flimarketImageUrl);
        store.setFlimarketSectionCnt(this.flimarketSectionCnt == null ? 0 : this.flimarketSectionCnt);
    }
}
