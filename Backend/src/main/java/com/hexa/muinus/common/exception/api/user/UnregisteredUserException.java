package com.hexa.muinus.common.exception.api.user;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class UnregisteredUserException extends MuinusException {
    public UnregisteredUserException() {
        super(APIErrorCode.UNREGISTERED_USER);
    }
}