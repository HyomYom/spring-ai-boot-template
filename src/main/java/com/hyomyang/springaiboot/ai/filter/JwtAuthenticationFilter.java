package com.hyomyang.springaiboot.ai.filter;

import com.hyomyang.springaiboot.ai.component.TokenExtractor;
import com.hyomyang.springaiboot.ai.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider tokenProvider;



    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = TokenExtractor.extractBearer(request.getHeader("Authorization"));

        if(token != null){
            try {
                Jws<Claims> jws = tokenProvider.parseToken(token);

                // refresh 토큰으로 접근 시도 차단(인증으로 인정 X)
                if(!tokenProvider.isRefresh(jws)){
                    // 여기서는 그냥 인증 세팅 안 하고 통과시키면,
                    // 보호된 엔드포인트에서 401로 떨어짐(표준 응답은 entryPoint가 처리)
                    Long userId = Long.valueOf(jws.getPayload().getSubject());
                    var auth = new UsernamePasswordAuthenticationToken(userId, null, List.of());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }

        }
        filterChain.doFilter(request, response);
    }
}
