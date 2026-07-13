package com.mutsa.delivery.user.service;

import com.mutsa.delivery.global.apiPayload.code.GeneralErrorCode;
import com.mutsa.delivery.global.apiPayload.exception.ProjectException;
import com.mutsa.delivery.user.dto.response.UserResponseDto;
import com.mutsa.delivery.user.entity.User;
import com.mutsa.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponseDto getMyInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.USER_NOT_FOUND));
        return UserResponseDto.from(user);
    }
}