package com.hyomyang.springaiboot.ai.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyomyang.springaiboot.ai.logger.TestLogger;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(TestLogger.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class AuthFlowIntegrationTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;



    @Test
    void login_shouldIssueAccessAndRefresh() throws Exception {
        Map<String, String> req = Map.of("username", "test", "password", "1234");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.data.refreshToken").isNotEmpty());
    }


    @Test
    void refresh_rotation_shouldRejectReuse() throws Exception {
        // 1) login
        Map<String, String> req = Map.of("username", "test", "password", "1234");
        String loginsRes = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String r1 = JsonPath.read(loginsRes, "$.data.refreshToken");
    }




}
