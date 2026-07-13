package com.mutsa.delivery.user.controller;

import com.mutsa.delivery.global.apiPayload.ApiResponse;
import com.mutsa.delivery.global.security.JwtUserPrincipal;
import com.mutsa.delivery.user.dto.response.UserResponseDto;
import com.mutsa.delivery.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDto>> getMyInfo(@AuthenticationPrincipal JwtUserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.onSuccess(userService.getMyInfo(principal.userId())));
    }
}
