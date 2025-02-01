package com.hexa.muinus.store.dto.kiosk;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScanBarcodeResponseDTO {

    private String itemName;
    private Integer price;
}
