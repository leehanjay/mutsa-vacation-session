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

    public static final String AUTH_EXCEPTION_ATTRIBUTE = "authException";

    private static final String BEARER_PREFIX = JwtTokenProvider.BEARER_TYPE + " ";

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        for (String pattern : SecurityWhitelist.PERMIT_ALL_URIS) {
            if (PATH_MATCHER.match(pattern, path)) {
                return true;
            }
        }

        if ("GET".equalsIgnoreCase(request.getMethod())) {
            for (String pattern : SecurityWhitelist.PERMIT_ALL_GET_URIS) {
                if (PATH_MATCHER.match(pattern, path)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = resolveToken(request);

        if (token != null) {
            try {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ProjectException e) {
                request.setAttribute(AUTH_EXCEPTION_ATTRIBUTE, e);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken)
                && bearerToken.regionMatches(true, 0, BEARER_PREFIX, 0, BEARER_PREFIX.length())) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
