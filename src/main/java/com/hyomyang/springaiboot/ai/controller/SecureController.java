package com.hyomyang.springaiboot.ai.controller;


import com.hyomyang.springaiboot.ai.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secure")
public class SecureController {

    @GetMapping("/ping")
    public ResponseEntity<ApiResponse<String>> ping(Authentication auth){

        String msg = "pong:" + auth.getPrincipal();
        return ResponseEntity.ok(ApiResponse.ok(msg));
    }
}
