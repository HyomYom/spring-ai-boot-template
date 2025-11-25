package com.hyomyang.springaiboot.ai.handler;


import com.hyomyang.springaiboot.ai.dto.error.ErrorCode;
import com.hyomyang.springaiboot.ai.dto.error.ErrorResponse;
import com.hyomyang.springaiboot.ai.dto.error.FieldErrorDetail;
import com.hyomyang.springaiboot.ai.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1) @Valid 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handelValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest req) {
        List<FieldErrorDetail> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldErrorDetail::from)
                .toList();

        log.warn("Validation failed: {} {}", req.getRequestURI(), fieldErrors, ex);

        ErrorResponse response = ErrorResponse.of(
                ErrorCode.VALIDATION_FAILED,
                req.getRequestURI(),
                fieldErrors
        );
        return ResponseEntity.status(ErrorCode.VALIDATION_FAILED.getStatus())
                .body(ApiResponse.error(ErrorCode.VALIDATION_FAILED.getMessage(),
                        response));
    };

    // 2) JSON 파싱 오류
    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleMalformedJson(
            Exception ex,
            HttpServletRequest request
    ) {
        log.warn("Malformed JSON: {}", request.getRequestURI(), ex);

        ErrorResponse response = ErrorResponse.of(
                ErrorCode.MALFORMED_JSON,
                request.getRequestURI()
        );

        return ResponseEntity
                .status(ErrorCode.MALFORMED_JSON.getStatus())
                .body(ApiResponse.error(
                        ErrorCode.MALFORMED_JSON.getMessage(),
                        response
                ));
    }

    // 3) 그 외 모든 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleException(
            Exception ex,
            HttpServletRequest request
    ) {
        log.error("Unexpected error: {}", request.getRequestURI(), ex);

        ErrorResponse response = ErrorResponse.of(
                ErrorCode.BUSINESS_ERROR,
                request.getRequestURI()
        );

        return ResponseEntity
                .status(ErrorCode.BUSINESS_ERROR.getStatus())
                .body(ApiResponse.error(
                        ErrorCode.BUSINESS_ERROR.getMessage(),
                        response
                ));
    }

}
