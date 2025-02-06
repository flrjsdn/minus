package com.hexa.muinus.common.exception.api.item;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class ItemNotFoundException extends MuinusException {
    public ItemNotFoundException() {
        super(APIErrorCode.ITEM_NOT_FOR_SALE);
    }

    public ItemNotFoundException(int itemId) {
        super(APIErrorCode.ITEM_NOT_FOR_SALE, String.format("itemId: %d", itemId));
    }

    public ItemNotFoundException(String barcode) {
        super(APIErrorCode.ITEM_NOT_FOR_SALE, String.format("barcode: %s", barcode));
    }


}
