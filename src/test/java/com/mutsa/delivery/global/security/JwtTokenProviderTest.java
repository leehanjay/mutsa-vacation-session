package com.mutsa.delivery.global.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.mutsa.delivery.global.apiPayload.exception.ProjectException;
import com.mutsa.delivery.global.security.exception.AuthErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

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
    void 발급한_토큰에서_userId와_email을_그대로_꺼낼_수_있다() {
        String token = jwtTokenProvider.createToken(1L, "test@mutsa.com");

        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        JwtUserPrincipal principal = (JwtUserPrincipal) authentication.getPrincipal();

        assertThat(principal.userId()).isEqualTo(1L);
        assertThat(principal.email()).isEqualTo("test@mutsa.com");
    }

    @Test
    void 만료시간이_지난_토큰은_EXPIRED_TOKEN_예외가_발생한다() throws InterruptedException {
        JwtTokenProvider shortLivedProvider = new JwtTokenProvider(
                "test-secret-key-for-jwt-signing-must-be-long-enough-256bit",
                1L
        );
        String token = shortLivedProvider.createToken(1L, "test@mutsa.com");

        Thread.sleep(10);

        assertThatThrownBy(() -> shortLivedProvider.getAuthentication(token))
                .isInstanceOf(ProjectException.class)
                .extracting(e -> ((ProjectException) e).getErrorCode())
                .isEqualTo(AuthErrorCode.EXPIRED_TOKEN);
    }

    @Test
    void 다른_시크릿으로_서명된_토큰은_INVALID_TOKEN_예외가_발생한다() {
        JwtTokenProvider otherProvider = new JwtTokenProvider(
                "another-secret-key-for-jwt-signing-must-be-long-enough",
                3600000L
        );
        String token = otherProvider.createToken(1L, "test@mutsa.com");

        assertThatThrownBy(() -> jwtTokenProvider.getAuthentication(token))
                .isInstanceOf(ProjectException.class)
                .extracting(e -> ((ProjectException) e).getErrorCode())
                .isEqualTo(AuthErrorCode.INVALID_TOKEN);
    }

    @Test
    void JWT_형식이_아닌_문자열은_INVALID_TOKEN_예외가_발생한다() {
        assertThatThrownBy(() -> jwtTokenProvider.getAuthentication("this-is-not-a-jwt"))
                .isInstanceOf(ProjectException.class)
                .extracting(e -> ((ProjectException) e).getErrorCode())
                .isEqualTo(AuthErrorCode.INVALID_TOKEN);
    }
}
