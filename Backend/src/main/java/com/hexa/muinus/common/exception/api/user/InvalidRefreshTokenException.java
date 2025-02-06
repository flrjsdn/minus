package com.hexa.muinus.common.exception.api.user;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class InvalidRefreshTokenException extends MuinusException {
    public InvalidRefreshTokenException() {
        super(APIErrorCode.INVALID_REFRESH_TOKEN);
    }
}