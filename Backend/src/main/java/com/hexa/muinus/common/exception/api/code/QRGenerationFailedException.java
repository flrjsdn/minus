package com.hexa.muinus.common.exception.api.code;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class QRGenerationFailedException extends MuinusException {
    public QRGenerationFailedException() {
        super(APIErrorCode.QR_GENERATION_FAILED);
    }
}