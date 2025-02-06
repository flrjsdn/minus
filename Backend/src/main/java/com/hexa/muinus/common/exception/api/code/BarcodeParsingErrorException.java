package com.hexa.muinus.common.exception.api.code;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class BarcodeParsingErrorException extends MuinusException {
    public BarcodeParsingErrorException() {
        super(APIErrorCode.BARCODE_PARSING_ERROR);
    }
}