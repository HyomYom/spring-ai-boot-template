package com.hyomyang.springaiboot.ai.exception;

import com.hyomyang.springaiboot.ai.dto.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class UnauthorizedException extends RuntimeException {

    private final ErrorCode errorCode;

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
