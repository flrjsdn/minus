package com.hexa.muinus.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScanBarcodeRequestDTO {

    private Integer storeNo;
    private String barcode;
}
