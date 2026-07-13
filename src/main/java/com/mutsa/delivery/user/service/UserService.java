package com.mutsa.delivery.user.service;

import com.mutsa.delivery.global.apiPayload.code.GeneralErrorCode;
import com.mutsa.delivery.global.apiPayload.exception.ProjectException;
import com.mutsa.delivery.global.security.JwtTokenProvider;
import com.mutsa.delivery.global.security.exception.AuthErrorCode;
import com.mutsa.delivery.user.dto.request.SignupRequestDto;
import com.mutsa.delivery.user.dto.response.UserResponseDto;
import com.mutsa.delivery.user.entity.User;
import com.mutsa.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @Transactional
    public void signup(SignupRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new ProjectException(AuthErrorCode.ALREADY_EXIST_EMAIL);
        }
        if (userRepository.existsByNickname(requestDto.getNickname())) {
            throw new ProjectException(AuthErrorCode.ALREADY_EXIST_NICKNAME);
        }

        // 비밀번호를 Bcrypt 기법으로 안전하게 암호화하여 저장
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = User.createNew(requestDto.getEmail(), encodedPassword, requestDto.getNickname());

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getMyInfo() {
        // JwtAuthenticationFilter가 채워둔 SecurityContext에서 인증 객체의 Name(=userId)을 꺼냄
        String currentUserIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
        Long currentUserId = Long.parseLong(currentUserIdStr);

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.USER_NOT_FOUND));
        return UserResponseDto.from(user);
    }
}