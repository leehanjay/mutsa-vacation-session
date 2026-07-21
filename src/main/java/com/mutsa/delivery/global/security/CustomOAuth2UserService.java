package com.mutsa.delivery.global.security;

import com.mutsa.delivery.user.entity.SocialType;
import com.mutsa.delivery.user.entity.User;
import com.mutsa.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final String ROLE_USER = "ROLE_USER";
    private static final String DEFAULT_NICKNAME = "카카오유저";

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String socialId = String.valueOf(attributes.get("id"));

        Map<String, Object> kakaoAccount = asMap(attributes.get("kakao_account"));
        Map<String, Object> profile = kakaoAccount == null ? null : asMap(kakaoAccount.get("profile"));

        String email = kakaoAccount == null ? null : (String) kakaoAccount.get("email");
        String nickname = profile == null ? null : (String) profile.get("nickname");

        User user = userRepository.findBySocialTypeAndSocialId(SocialType.KAKAO, socialId)
                .orElseGet(() -> userRepository.save(
                        User.createSocial(email, resolveNickname(nickname), SocialType.KAKAO, socialId)
                ));

        Map<String, Object> customAttributes = new HashMap<>(attributes);
        customAttributes.put("userId", user.getUserId());
        customAttributes.put("email", user.getEmail());

        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority(ROLE_USER)),
                customAttributes,
                "id"
        );
    }

    // 카카오 기본 닉네임("카카오유저")이 겹치는 경우를 대비해 닉네임 unique 제약 위반 없이 가입되도록 처리
    private String resolveNickname(String nickname) {
        String candidate = (nickname == null || nickname.isBlank()) ? DEFAULT_NICKNAME : nickname;
        while (userRepository.existsByNickname(candidate)) {
            candidate = candidate + "_" + UUID.randomUUID().toString().substring(0, 6);
        }
        return candidate;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> asMap(Object value) {
        return value instanceof Map ? (Map<String, Object>) value : null;
    }
}
