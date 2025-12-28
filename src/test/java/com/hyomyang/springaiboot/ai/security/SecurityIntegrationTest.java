package com.hyomyang.springaiboot.ai.security;


import com.hyomyang.springaiboot.ai.logger.TestLogger;
import com.hyomyang.springaiboot.ai.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(TestLogger.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    JwtTokenProvider tokenProvider;

    @Test
    void noToken_shouldReturn401() throws Exception {
        mockMvc.perform(get("/api/secure/ping"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
        ;
    }

    @Test
    void accessToken_shouldReturn200() throws Exception {
        String accessToken = tokenProvider.createAccessToken(1L);
        mockMvc.perform(get("/api/secure/ping")
                .header("Authorization","Bearer " + accessToken ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value("pong:1"));

    }

    @Test
    void refreshToken_shouldReturn401() throws Exception {
        String refreshToken = tokenProvider.createRefreshToken(1L);

        mockMvc.perform(get("/api/secure/ping")
                .header("Authorization","Bearer " + refreshToken ))
                .andExpect(status().isUnauthorized());
    }
}
