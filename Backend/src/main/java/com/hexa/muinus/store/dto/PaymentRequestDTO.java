package com.hexa.muinus.store.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaymentRequestDTO {

    private List<ItemForPayment> itemForPayment;
}

@Getter
@Setter
class ItemForPayment {
    private String itemName;
    private Integer quantity;
    private Integer totalPrice;
}
