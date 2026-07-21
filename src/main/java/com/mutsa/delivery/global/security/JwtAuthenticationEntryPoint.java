package com.mutsa.delivery.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mutsa.delivery.global.apiPayload.code.GeneralErrorCode;
import com.mutsa.delivery.global.apiPayload.exception.ProjectException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
// 401(누군지도 모름)
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request, // 들어온 요청 그 자체
            HttpServletResponse response, // 나갈 응답, 아직 비어있는 상자
            AuthenticationException authException // 사실 안 씀
    ) throws IOException {
        ProjectException exception = resolveException(request); // 직접 예외를 다시 찾음, 우리가 저장해 놓은 예외를 사용

        // 실제 HTTP 응답 바디(JSON)와 상태코드를 작성
        SecurityErrorResponseWriter.write(
                response, objectMapper, exception.getErrorCode(), exception.getMessage()
        );
    }

    private ProjectException resolveException(HttpServletRequest request) {
        // 필터가 저장해둔 값을 꺼냄(왜 인증 실패를 했는지)
        Object attribute = request.getAttribute(JwtAuthenticationFilter.AUTH_EXCEPTION_ATTRIBUTE);
        if (attribute instanceof ProjectException e) {
            return e;
        }
        // 매칭 실패 시 기본 UNAUTHORIZED 반환
        return new ProjectException(GeneralErrorCode.UNAUTHORIZED);
    }
}
