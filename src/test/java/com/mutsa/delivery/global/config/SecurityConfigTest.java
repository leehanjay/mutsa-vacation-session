package com.mutsa.delivery.global.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mutsa.delivery.global.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void 인증이_필요없는_경로는_토큰_없이_접근할_수_있다() throws Exception {
        mockMvc.perform(get("/stores"))
                .andExpect(status().isOk());
    }

    @Test
    void 토큰_없이_보호된_경로에_접근하면_401과_COMMON401을_반환한다() throws Exception {
        mockMvc.perform(get("/users/me"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("COMMON401"));
    }

    @Test
    void 만료된_토큰으로_보호된_경로에_접근하면_401과_AUTH401_1을_반환한다() throws Exception {
        JwtTokenProvider shortLivedProvider = new JwtTokenProvider(
                "test-secret-key-for-jwt-signing-must-be-long-enough-256bit",
                1L
        );
        String token = shortLivedProvider.createToken(1L, "test@mutsa.com");
        Thread.sleep(10);

        mockMvc.perform(get("/users/me").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("AUTH401_1"));
    }

    @Test
    void 유효한_토큰이면_보안필터를_통과해_비즈니스_로직까지_도달한다() throws Exception {
        // data.sql에 시드된 user_id=1 기준
        String token = jwtTokenProvider.createToken(1L, "test@mutsa.com");

        mockMvc.perform(get("/users/me").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk());
    }
}
