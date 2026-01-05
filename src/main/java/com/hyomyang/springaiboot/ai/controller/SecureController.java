package com.hyomyang.springaiboot.ai.controller;


import com.hyomyang.springaiboot.ai.dto.response.ApiResponse;
import com.hyomyang.springaiboot.ai.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secure")
public class SecureController {

    @GetMapping("/ping")
    public ResponseEntity<ApiResponse<String>> ping(@AuthenticationPrincipal UserPrincipal userPrincipal){

        String msg = "pong:" + userPrincipal.userId();
        return ResponseEntity.ok(ApiResponse.ok(msg));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/ping")
    public ResponseEntity<ApiResponse<String>> adminPing(@AuthenticationPrincipal UserPrincipal userPrincipal){
        String msg = "pong:" + userPrincipal.userId();
        return ResponseEntity.ok(ApiResponse.ok(msg));
    }
}
