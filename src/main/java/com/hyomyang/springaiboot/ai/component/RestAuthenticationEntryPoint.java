package com.hyomyang.springaiboot.ai.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyomyang.springaiboot.ai.dto.error.ErrorCode;
import com.hyomyang.springaiboot.ai.dto.error.ErrorResponse;
import com.hyomyang.springaiboot.ai.dto.response.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;



    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ErrorResponse err = ErrorResponse.of(
                ErrorCode.UNAUTHORIZED,
                request.getRequestURI()
        );

        ApiResponse<ErrorResponse> body = ApiResponse.error(
                ErrorCode.UNAUTHORIZED.getMessage()
        );

        objectMapper.writeValue(response.getWriter(), body);
    }
}
