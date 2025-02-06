package com.hexa.muinus.common.exception.api.store;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class StoreRegistrationNoDuplicateException extends MuinusException {
    public StoreRegistrationNoDuplicateException() {
        super(APIErrorCode.STORE_REGISTRATION_NO_DUPLICATE);
    }
    public StoreRegistrationNoDuplicateException(String registrationNo) {
        super(APIErrorCode.STORE_REGISTRATION_NO_DUPLICATE, String.format("registrationNo: %s", registrationNo));
    }
}
