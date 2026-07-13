package com.mutsa.delivery.global.security;

import com.mutsa.delivery.global.apiPayload.exception.ProjectException;
import com.mutsa.delivery.global.security.exception.AuthErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j // log라는 필드가 이미 존재하는 것처럼 해 줌
@Component
public class JwtTokenProvider {

    public static final String BEARER_TYPE = "Bearer";

    private static final String CLAIM_EMAIL = "email";

    private final SecretKey secretKey;
    private final long expiration;
    private final JwtParser jwtParser;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }

    public String createToken(Long userId, String email) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim(CLAIM_EMAIL, email)
                .issuedAt(now)
                .expiration(expiresAt)
                .signWith(secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        JwtUserPrincipal principal = new JwtUserPrincipal(
                parseUserId(claims),
                claims.get(CLAIM_EMAIL, String.class)
        );
        return new UsernamePasswordAuthenticationToken(principal, null, List.of());
    }

    private Claims parseClaims(String token) {
        try {
            return jwtParser.parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            log.debug("만료된 JWT 토큰입니다: {}", e.getMessage());
            throw new ProjectException(AuthErrorCode.EXPIRED_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            log.debug("유효하지 않은 JWT 토큰입니다: {}", e.getMessage());
            throw new ProjectException(AuthErrorCode.INVALID_TOKEN);
        }
    }

    private Long parseUserId(Claims claims) {
        try {
            return Long.valueOf(claims.getSubject());
        } catch (NumberFormatException e) {
            log.debug("토큰의 subject가 숫자 형식이 아닙니다: {}", claims.getSubject());
            throw new ProjectException(AuthErrorCode.INVALID_TOKEN);
        }
    }
}
