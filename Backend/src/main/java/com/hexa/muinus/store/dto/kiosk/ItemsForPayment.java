package com.hexa.muinus.store.dto.kiosk;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemsForPayment {

    private Integer itemId;
    private String itemName;
    private Integer quantity;
    private Integer price;
    private Integer subtotal;
}
