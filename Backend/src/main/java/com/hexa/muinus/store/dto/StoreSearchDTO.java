package com.hexa.muinus.store.dto;

import com.hexa.muinus.common.enums.YesNo;
import com.hexa.muinus.store.domain.store.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreSearchDTO {
    private Integer storeNo;
    private String storeName;
    private BigDecimal locationX;
    private BigDecimal locationY;
    private String address;
    private String phone;
    private String itemName;
    private int salePrice;
    private int discountRate;
    private int discountPrice;
    private int quantity;
    private YesNo flimarketYn;
}
