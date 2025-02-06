package com.hexa.muinus.common.exception.api.store;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class StoreAlreadyRegisteredException extends MuinusException {
    public StoreAlreadyRegisteredException() {
        super(APIErrorCode.STORE_ALREADY_REGISTERED);
    }
    public StoreAlreadyRegisteredException(String userEmail) {
        super(APIErrorCode.STORE_ALREADY_REGISTERED, String.format("userEmail: %s", userEmail));
    }
}
