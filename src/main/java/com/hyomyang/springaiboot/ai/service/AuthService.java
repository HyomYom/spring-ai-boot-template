package com.hyomyang.springaiboot.ai.service;

import com.hyomyang.springaiboot.ai.dto.auth.LoginRequest;
import com.hyomyang.springaiboot.ai.dto.auth.TokenPair;
import com.hyomyang.springaiboot.ai.dto.error.ErrorCode;
import com.hyomyang.springaiboot.ai.exception.UnauthorizedException;
import com.hyomyang.springaiboot.ai.security.jwt.JwtTokenProvider;
import com.hyomyang.springaiboot.ai.security.refresh.RefreshTokenStore;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenStore refreshTokenStore;

    public TokenPair login(LoginRequest req){
        // 추후 진짜 로그인으로 벼녁ㅇ
        Long userId = 1L; //TODO: 실제로는 username/pw 검증 후 조회된 userId
        
        String access = tokenProvider.createAccessToken(userId);
        String refresh = tokenProvider.createRefreshToken(userId);

        Jws<Claims> refreshJws = tokenProvider.parseToken(refresh);
        refreshTokenStore.save(
                userId,
                tokenProvider.getJti(refreshJws),
                tokenProvider.getExpires(refreshJws)
        );
        return new TokenPair(access, refresh);

    }

    public TokenPair refresh(String refreshToken){
        if(refreshToken == null || refreshToken.isBlank()){
            throw new UnauthorizedException(ErrorCode.TOKEN_INVALID);
        }
        Jws<Claims> jws;

        try {
            jws = tokenProvider.parseToken(refreshToken);
        } catch (Exception e) {
            throw new UnauthorizedException(ErrorCode.TOKEN_INVALID);
        }

        return null;
    }
}
