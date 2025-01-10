package com.sparta.nalda.exception;


public class NdException extends RuntimeException {

    private final ErrorCode errorCode;

    public NdException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}

