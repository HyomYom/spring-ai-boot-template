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

        if(!tokenProvider.isRefresh(jws)){
            throw new UnauthorizedException(ErrorCode.TOKEN_TYPE_MISMATCH);
        }

        Long userId = tokenProvider.getSubject(refreshToken);
        String jti = tokenProvider.getJti(jws);

        if(!refreshTokenStore.exists(userId,jti)){
            // 이미 폐기되었거나(로그아웃/rotation), 재사용 공격
            throw new UnauthorizedException(ErrorCode.REFRESH_REVOKED_OR_REUSED);
        }

        // rotation: 기존 refresh 폐기
        refreshTokenStore.revoke(userId,jti);

        // 새 refresh
        String newAccess = tokenProvider.createAccessToken(userId);
        String newRefresh = tokenProvider.createRefreshToken(userId);

        // 새 refresh 저장
        Jws<Claims> newRefreshJws = tokenProvider.parseToken(newRefresh);
        refreshTokenStore.save(
                userId,
                tokenProvider.getJti(newRefreshJws),
                tokenProvider.getExpires(newRefreshJws)
        );

        return new TokenPair(newAccess, newRefresh);


    }

    public void logout(String refreshToken){
        if(refreshToken == null | refreshToken.isBlank()) return;

        try {
            Jws<Claims> jws = tokenProvider.parseToken(refreshToken);

            if(!tokenProvider.isRefresh(jws))return;

            Long userId = tokenProvider.getSubject(refreshToken);
            String jti = tokenProvider.getJti(jws);

            refreshTokenStore.revoke(userId, jti);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
