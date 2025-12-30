package com.hyomyang.springaiboot.ai.security.jwt;

import com.hyomyang.springaiboot.ai.config.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {
    private final SecretKey key;
    private final JwtProperties jwtProps;
    private final Clock clock;


    public JwtTokenProvider(
            @Qualifier("jwtSecretKey") SecretKey key,
            JwtProperties jwtProps,
            Clock clock
            ) {
        this.key = key;
        this.jwtProps = jwtProps;
        this.clock = clock;
    }

    public String createAccessToken(Long userId) {
        Instant now = Instant.now(clock);
        Instant exp = now.plus(jwtProps.accessTokenExpMin(), ChronoUnit.MINUTES);

        return Jwts.builder()
                .subject(String.valueOf(userId)) //sub claim
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .claim("type","access")
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public String createRefreshToken(Long userId) {
        Instant now = Instant.now(clock);
        Instant exp = now.plus(jwtProps.refreshTokenExpDays(), ChronoUnit.DAYS);

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .claim("type", "refresh")
                .id(UUID.randomUUID().toString())   // 로데이션/폐기 관리에 유용
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }


    // == getUserId
    public Long getSubject(String token){
        String sub = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

        return Long.parseLong(sub);
    }

    public String getJti(Jws<Claims> jws) {
        return jws.getPayload().getId();
    }

    public Instant getExpires(Jws<Claims> jws) {
        return jws.getPayload().getExpiration().toInstant();
    }

    public boolean validate(String token){
        try{
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean isRefresh(Jws<Claims> jws){
        return "refresh".equals(jws.getPayload().get("type", String.class));
    }

}
