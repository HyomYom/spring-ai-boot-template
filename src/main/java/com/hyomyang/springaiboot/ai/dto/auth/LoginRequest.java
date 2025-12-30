package com.hyomyang.springaiboot.ai.dto.auth;

public record LoginRequest(
        String username,
        String password
) {
}
