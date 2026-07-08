package com.mutsa.delivery.order.controller;

import com.mutsa.delivery.order.dto.request.OrderRequestDto;
import com.mutsa.delivery.order.dto.response.OrderResponseDto;
import com.mutsa.delivery.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Map<String,Object>> createOrder(@RequestBody OrderRequestDto requestDto){
        Long dummyUserId = 1L;

        OrderResponseDto responseDto = orderService.createOrder(dummyUserId, requestDto);

        // 팀 공통 응답 포맷에 맞춰 201 Created 상태 코드로 응답
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "success", true,
                "message", "주문 및 결제가 성공적으로 완료되었습니다.",
                "data", responseDto
        ));
    }
}
