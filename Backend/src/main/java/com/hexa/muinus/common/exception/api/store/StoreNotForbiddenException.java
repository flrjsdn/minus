package com.hexa.muinus.common.exception.api.store;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class StoreNotForbiddenException extends MuinusException {
    public StoreNotForbiddenException() {
        super(APIErrorCode.STORE_NOT_FORBIDDEN);
    }
    public StoreNotForbiddenException(Integer userNo, Integer storeNo) {
        super(APIErrorCode.STORE_NOT_FORBIDDEN, String.format("userNo: %d, storeNo: %d", userNo, storeNo));
    }

    public StoreNotForbiddenException(String email, Integer storeNo) {
        super(APIErrorCode.STORE_NOT_FORBIDDEN, String.format("email: %s, storeNo: %d", email, storeNo));
    }

    public StoreNotForbiddenException(String email) {
        super(APIErrorCode.STORE_NOT_FORBIDDEN, String.format("email: %s", email));
    }
}
