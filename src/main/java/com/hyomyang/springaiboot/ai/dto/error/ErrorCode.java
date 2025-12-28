package com.hyomyang.springaiboot.ai.dto.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "C001","입력값이 잘못되었습니다."),
    MALFORMED_JSON(HttpStatus.BAD_REQUEST, "C002", "요청 JSON 형식이 올바르지 않습니다."),
    BUSINESS_ERROR(HttpStatus.CONFLICT, "C003", "비즈니스 로직 오류가 발생했습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "C004", "유요한 인증정보가 없습니다"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "C005", "접근 권한이 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
