package com.hexa.muinus.common.exception.api.general;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class InternalServerErrorException extends MuinusException {
    public InternalServerErrorException() {
        super(APIErrorCode.INTERNAL_SERVER_ERROR);
    }
}
