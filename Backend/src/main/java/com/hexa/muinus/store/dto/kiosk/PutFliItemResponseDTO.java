package com.hexa.muinus.store.dto.kiosk;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PutFliItemResponseDTO {

    private String itemName;
    private Integer price;
}
