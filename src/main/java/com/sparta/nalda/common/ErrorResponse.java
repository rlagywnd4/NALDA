package com.sparta.nalda.common;

import com.sparta.nalda.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private final int status;
    private final String message;
    private final String reason;

    @Builder
    public ErrorResponse(final int status, final String message, final String reason) {
        this.status = status;
        this.message = message;
        this.reason = reason;
    }

    public static ErrorResponse of(final ErrorCode errorCode, final String reason) {
        return ErrorResponse.builder()
            .status(errorCode.getStatus())
            .message(errorCode.getMessage())
            .reason(reason)
            .build();
    }

    public static ErrorResponse of(final ErrorCode errorCode) {
        return ErrorResponse.builder()
            .status(errorCode.getStatus())
            .message(errorCode.getMessage())
            .reason(errorCode.getMessage())
            .build();
    }
}
