package com.hexa.muinus.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreItemDTO {
    private int storeItemId;
    private int itemId;
    private String itemName;
    private String itemImageUrl;
    private int quantity;
    private int salePrice;
    private int discountRate;
    private int discountPrice;
}
