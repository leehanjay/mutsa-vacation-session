package com.mutsa.delivery.order.controller;

import com.mutsa.delivery.global.apiPayload.ApiResponse;
import com.mutsa.delivery.global.apiPayload.code.GeneralSuccessCode;
import com.mutsa.delivery.global.security.JwtUserPrincipal;
import com.mutsa.delivery.order.dto.request.OrderRequestDto;
import com.mutsa.delivery.order.dto.response.OrderResponseDto;
import com.mutsa.delivery.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(
            @AuthenticationPrincipal JwtUserPrincipal principal,
            @Valid @RequestBody OrderRequestDto requestDto) {

        // 더미 데이터 삭제 후 principal.userId() 매핑
        OrderResponseDto responseDto = orderService.createOrder(principal.userId(), requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(GeneralSuccessCode.CREATED, responseDto));
    }
}
