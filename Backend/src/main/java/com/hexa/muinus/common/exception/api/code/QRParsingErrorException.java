package com.hexa.muinus.common.exception.api.code;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class QRParsingErrorException extends MuinusException {
    public QRParsingErrorException() {
        super(APIErrorCode.QR_PARSING_ERROR);
    }
}