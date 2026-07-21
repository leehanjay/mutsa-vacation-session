package com.mutsa.delivery.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mutsa.delivery.global.apiPayload.code.GeneralErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
// 403(누군지는 알지만 권한이 없음) - 인증은 성공했으니 인증 실패 시 던지는 커스텀 예외 저장 로직이 필요 X
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        SecurityErrorResponseWriter.write(
                response, objectMapper, GeneralErrorCode.FORBIDDEN, GeneralErrorCode.FORBIDDEN.getMessage()
        );
    }
}
