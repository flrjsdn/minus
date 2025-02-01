package com.hexa.muinus.store.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class PutFliItemResponseDTO {

    private String itemName;
    private Integer price;
}
