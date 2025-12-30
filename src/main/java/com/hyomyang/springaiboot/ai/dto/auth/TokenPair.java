package com.hyomyang.springaiboot.ai.dto.auth;

public record TokenPair(
        String accessToken,
        String refreshToken
) {
}
