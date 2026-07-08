package com.mutsa.delivery.user.dto.response;

import com.mutsa.delivery.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {
    private Long userId;
    private String loginId;
    private Long credit;

    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .loginId(user.getLoginId())
                .credit(user.getCredit())
                .build();
    }
}