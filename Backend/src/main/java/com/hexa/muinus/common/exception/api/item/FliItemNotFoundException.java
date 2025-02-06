package com.hexa.muinus.common.exception.api.item;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class FliItemNotFoundException extends MuinusException {
    public FliItemNotFoundException() {
        super(APIErrorCode.FLI_ITEM_NOT_FOUND);
    }

    public FliItemNotFoundException(int fliItemId) {
        super(APIErrorCode.SECTION_FLI_ITEM_NOT_FOUND, String.format("fliItemId: %d", fliItemId));
    }

    public FliItemNotFoundException(int storeNo, int fliItemId) {
        super(APIErrorCode.SECTION_FLI_ITEM_NOT_FOUND, String.format("storeNo: %d, fliItemId: %d", storeNo, fliItemId));
    }
}
