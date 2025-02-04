package com.hexa.muinus.store.dto.kiosk;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FliItemsForPayment {

    private Integer fliItemId;
    private String fliItemName;
    private Integer quantity;
    private Integer price;
    private Integer subtotal;
}
