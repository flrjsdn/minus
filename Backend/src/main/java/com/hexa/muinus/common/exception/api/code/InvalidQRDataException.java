package com.hexa.muinus.common.exception.api.code;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class InvalidQRDataException extends MuinusException {
    public InvalidQRDataException() {
        super(APIErrorCode.INVALID_QR_DATA);
    }
}