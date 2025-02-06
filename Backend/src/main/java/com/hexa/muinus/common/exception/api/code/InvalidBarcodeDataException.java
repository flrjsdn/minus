package com.hexa.muinus.common.exception.api.code;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class InvalidBarcodeDataException extends MuinusException {
    public InvalidBarcodeDataException() {
        super(APIErrorCode.INVALID_BARCODE_DATA);
    }
}