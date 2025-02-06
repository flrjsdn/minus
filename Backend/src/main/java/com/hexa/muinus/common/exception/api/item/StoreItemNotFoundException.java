package com.hexa.muinus.common.exception.api.item;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class StoreItemNotFoundException extends MuinusException {
    public StoreItemNotFoundException() {
        super(APIErrorCode.STORE_ITEM_NOT_FOR_SALE);
    }

    public StoreItemNotFoundException(int itemId) {
        super(APIErrorCode.STORE_ITEM_NOT_FOR_SALE, "itemId : " + itemId);
    }

    public StoreItemNotFoundException(int storeNo, int itemId) {
        super(APIErrorCode.STORE_ITEM_NOT_FOR_SALE, String.format("itemId : %d, storeNo : %d", itemId, storeNo));
    }


}
