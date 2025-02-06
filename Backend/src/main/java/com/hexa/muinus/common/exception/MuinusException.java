package com.hexa.muinus.common.exception;

import lombok.Getter;

@Getter
public class MuinusException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String details;

    public MuinusException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.details = null;
    }

    public MuinusException(ErrorCode errorCode, String details) {
        super(errorCode.getMessage() + " | " + details);
        this.errorCode = errorCode;
        this.details = details;
    }

}


