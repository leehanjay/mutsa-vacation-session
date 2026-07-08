package com.mutsa.delivery.user.service;

import com.mutsa.delivery.user.dto.response.UserResponseDto;
import com.mutsa.delivery.user.entity.User;
import com.mutsa.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Long CURRENT_USER_ID = 1L; // 로그인 미구현: user_id=1을 현재 로그인 사용자로 간주

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponseDto getMyInfo() {
        User user = userRepository.findById(CURRENT_USER_ID)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. userId=" + CURRENT_USER_ID));
        return UserResponseDto.from(user);
    }
}