package com.mutsa.delivery.order.controller;

import com.mutsa.delivery.global.apiPayload.ApiResponse;
import com.mutsa.delivery.global.apiPayload.code.GeneralSuccessCode;
import com.mutsa.delivery.order.dto.request.OrderRequestDto;
import com.mutsa.delivery.order.dto.response.OrderResponseDto;
import com.mutsa.delivery.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(@Valid @RequestBody OrderRequestDto requestDto) { // 구체 타입 명시
        Long dummyUserId = 1L;

        OrderResponseDto responseDto = orderService.createOrder(dummyUserId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(GeneralSuccessCode.CREATED, responseDto));
    }
}
