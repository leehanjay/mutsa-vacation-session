package com.mutsa.delivery.user.dto.response;

import com.mutsa.delivery.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {
    private Long userId;
    private String email;
    private Long credit;

    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .credit(user.getCredit())
                .build();
    }
}