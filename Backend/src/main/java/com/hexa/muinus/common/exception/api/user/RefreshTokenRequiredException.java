package com.hexa.muinus.common.exception.api.user;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class RefreshTokenRequiredException extends MuinusException {
    public RefreshTokenRequiredException() {
        super(APIErrorCode.REFRESH_TOKEN_REQUIRED);
    }
}