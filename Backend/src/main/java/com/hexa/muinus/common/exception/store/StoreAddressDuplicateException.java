package com.hexa.muinus.common.exception.store;

import com.hexa.muinus.common.exception.ErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class StoreAddressDuplicateException extends MuinusException {
    public StoreAddressDuplicateException() {
        super(ErrorCode.STORE_ADDRESS_DUPLICATE);
    }
    public StoreAddressDuplicateException(String address) {
        super(ErrorCode.STORE_ADDRESS_DUPLICATE, String.format("address: %s", address));
    }
}
