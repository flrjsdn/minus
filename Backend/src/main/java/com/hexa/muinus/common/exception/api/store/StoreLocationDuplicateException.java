package com.hexa.muinus.common.exception.api.store;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

import java.math.BigDecimal;

public class StoreLocationDuplicateException extends MuinusException {
    public StoreLocationDuplicateException() {
        super(APIErrorCode.STORE_LOCATION_DUPLICATE);
    }
    public StoreLocationDuplicateException(BigDecimal x, BigDecimal y) {
        super(APIErrorCode.STORE_LOCATION_DUPLICATE, String.format("locationX: %s, locationY: %s", x, y));
    }
}
