package com.mutsa.delivery.global.security;

public final class SecurityWhitelist {

    // 로그인, 회원가입, swagger 문서는 완전 오픈
    public static final String[] PERMIT_ALL_URIS = {
            "/auth/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/login/oauth2/code/**"
    };

    // 조회(GET)만 열어주는 경로 - 같은 prefix로 쓰기 API가 추가돼도 자동으로 열리지 않도록 메서드를 제한함
    public static final String[] PERMIT_ALL_GET_URIS = {
            "/stores/**",
            "/categories/**"
    };

    private SecurityWhitelist() {
    }
}
