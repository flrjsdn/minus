package com.hexa.muinus.common.batch.exeption;

import lombok.Getter;

@Getter
public class BatchProcessingException extends RuntimeException {
    private final BatchErrorCode errorCode;

    public BatchProcessingException(BatchErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BatchProcessingException(BatchErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
}
