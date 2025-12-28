package com.hyomyang.springaiboot.ai.component;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyomyang.springaiboot.ai.dto.error.ErrorCode;
import com.hyomyang.springaiboot.ai.dto.error.ErrorResponse;
import com.hyomyang.springaiboot.ai.dto.response.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RestAccessDenieHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ErrorResponse err = ErrorResponse.of(
                ErrorCode.FORBIDDEN,
                request.getRequestURI()
        );

        ApiResponse<ErrorResponse> body = ApiResponse.error(
                err,
                ErrorCode.FORBIDDEN.getMessage()
                );

        objectMapper.writeValue(response.getWriter(), body);
    }
}
