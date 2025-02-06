package com.hexa.muinus.common.exception.api.general;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class UnauthorizedException extends MuinusException {
    public UnauthorizedException() {
        super(APIErrorCode.UNAUTHORIZED);
    }
}
