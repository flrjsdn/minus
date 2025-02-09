package com.hexa.muinus.store.dto.fli;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FliItemDTO {
    private int fliItemId;
    private int userNo;
    private String fliItemName;
    private int price;
    private int quantity;
    private String imagePath;
}
