package com.mutsa.delivery.global.security;

// 이 사용자가 누구인지 담는 불변 객체
public record JwtUserPrincipal(Long userId, String email) {
}
