package com.hexa.muinus.common.exception.api.general;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class GoneException extends MuinusException {
    public GoneException() {
        super(APIErrorCode.GONE);
    }
}
