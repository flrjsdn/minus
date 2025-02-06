package com.hexa.muinus.common.exception.api.store;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;
import jakarta.validation.constraints.NotNull;

public class StoreNotFoundException extends MuinusException {
    public StoreNotFoundException() {
        super(APIErrorCode.STORE_NOT_FOUND);
    }
    public StoreNotFoundException(@NotNull Integer storeNo) {
        super(APIErrorCode.STORE_NOT_FOUND, String.format("storeNo: %d", storeNo));
    }
}
