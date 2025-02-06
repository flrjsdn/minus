package com.hexa.muinus.common.exception.api.code;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class BarcodeGenerationFailedException extends MuinusException {
    public BarcodeGenerationFailedException() {
        super(APIErrorCode.BARCODE_GENERATION_FAILED);
    }
}