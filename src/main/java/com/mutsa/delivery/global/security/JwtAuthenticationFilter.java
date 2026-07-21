package com.mutsa.delivery.global.security;

import com.mutsa.delivery.global.apiPayload.exception.ProjectException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // 상수로서 request.setAttribute()에서 쓸 예외를 담아두는 키 이름
    public static final String AUTH_EXCEPTION_ATTRIBUTE = "authException";

    // 헤더에서 이 접두사를 자르기 위해서 만든 값
    private static final String BEARER_PREFIX = JwtTokenProvider.BEARER_TYPE + " ";

    // URL 패턴(와일드카드)과 실제 요청 경로가 매치되는지 비교해주는 스프링 유틸 객체
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    // 생성자 주입으로 받아옴
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // CORS preflight는 doFilterInternal을 건너뜀
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String path = request.getRequestURI();

        // SecurityWhitelist도 doFilterInternal을 건너뜀
        for (String pattern : SecurityWhitelist.PERMIT_ALL_URIS) {
            if (PATH_MATCHER.match(pattern, path)) {
                return true;
            }
        }

        // SecurityWhitelist도 doFilterInternal을 건너뜀
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            for (String pattern : SecurityWhitelist.PERMIT_ALL_GET_URIS) {
                if (PATH_MATCHER.match(pattern, path)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override // 부모 클래스의 매서드를 우리의 입맛대로 정의
    protected void doFilterInternal(
            HttpServletRequest request, // 들어온 요청 그 자체
            HttpServletResponse response, // 나갈 응답, 아직 비어있는 상자
            FilterChain filterChain // 필터 여러 개를 거쳐서 최종적으로 컨트롤러를 가리키는 객체
    ) throws ServletException, IOException {
        String token = resolveToken(request);

        if (token != null) {
            try {
                // 토큰을 검증하고 Authentication 객체(이 사용자는 인증됐다)를 만들어줌
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                // 이걸 SecurityContextHolder에 넣어줌
                SecurityContextHolder.getContext().setAuthentication(authentication);

            // 토큰이 유효하지 않을 때 예외가 터지는데 바로 예외를 던지지 않음
            } catch (ProjectException e) {
                request.setAttribute(AUTH_EXCEPTION_ATTRIBUTE, e);
            }
        }

        filterChain.doFilter(request, response); // 다음 단계로 넘겨라(인증에 실패해도)
    }

    private String resolveToken(HttpServletRequest request) {
        // 헤더에서 Authorization: 이라 된 토큰을 꺼내 옴
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        // 헤더 값이 비어있지 않고(hasText), 그 값이 Bearer로 시작하면
        if (StringUtils.hasText(bearerToken)
                && bearerToken.regionMatches(true, 0, BEARER_PREFIX, 0, BEARER_PREFIX.length())) {
            // 순수 토큰 문자열만 리턴
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
