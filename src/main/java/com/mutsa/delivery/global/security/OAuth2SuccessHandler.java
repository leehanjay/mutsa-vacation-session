package com.mutsa.delivery.global.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import com.mutsa.delivery.user.entity.User;
import com.mutsa.delivery.user.repository.UserRepository;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (kakaoAccount != null) ? (Map<String, Object>) kakaoAccount.get("profile") : null;

        String email = null;
        if (kakaoAccount != null && kakaoAccount.get("email") != null) {
            email = (String) kakaoAccount.get("email");
        } else {
            String kakaoId = String.valueOf(attributes.get("id"));
            email = kakaoId + "@kakao.user";
        }

        String nickname = (profile != null && profile.get("nickname") != null)
                ? (String) profile.get("nickname")
                : "카카오유저";

        final String userEmail = email;

        User user = userRepository.findByEmail(userEmail)
                .orElseGet(() -> {
                    String tempPassword = UUID.randomUUID().toString();
                    User newUser = User.createNew(userEmail, tempPassword, nickname);

                    return userRepository.save(newUser);
                });

        Long userId = user.getUserId();

        String accessToken = jwtTokenProvider.createToken(userId, userEmail);

        String targetUrl = UriComponentsBuilder.fromUriString("https://7th-delivery-app.vercel.app/oauth/redirect")
                .queryParam("accessToken", accessToken)
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}