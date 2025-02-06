package com.hexa.muinus.common.exception.api.general;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class BadRequestException extends MuinusException {
    public BadRequestException() {
        super(APIErrorCode.BAD_REQUEST);
    }
}
