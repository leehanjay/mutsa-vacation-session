package com.mutsa.delivery.global.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider(
                "test-secret-key-for-jwt-signing-must-be-long-enough-256bit",
                3600000L
        );
    }

    @Test
    void 토큰을_발급하면_유효성_검증을_통과한다() {
        String token = jwtTokenProvider.createToken(1L, "test@mutsa.com");

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    void 발급한_토큰에서_userId와_email을_그대로_꺼낼_수_있다() {
        String token = jwtTokenProvider.createToken(1L, "test@mutsa.com");

        assertThat(jwtTokenProvider.getUserId(token)).isEqualTo(1L);
        assertThat(jwtTokenProvider.getEmail(token)).isEqualTo("test@mutsa.com");
    }

    @Test
    void 만료시간이_지난_토큰은_검증에_실패한다() throws InterruptedException {
        JwtTokenProvider shortLivedProvider = new JwtTokenProvider(
                "test-secret-key-for-jwt-signing-must-be-long-enough-256bit",
                1L
        );
        String token = shortLivedProvider.createToken(1L, "test@mutsa.com");

        Thread.sleep(10);

        assertThat(shortLivedProvider.validateToken(token)).isFalse();
    }

    @Test
    void 다른_시크릿으로_서명된_토큰은_검증에_실패한다() {
        JwtTokenProvider otherProvider = new JwtTokenProvider(
                "another-secret-key-for-jwt-signing-must-be-long-enough",
                3600000L
        );
        String token = otherProvider.createToken(1L, "test@mutsa.com");

        assertThat(jwtTokenProvider.validateToken(token)).isFalse();
    }
}
