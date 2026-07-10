package com.mutsa.delivery.cart.controller;

import com.mutsa.delivery.cart.dto.request.CartItemAddRequestDto;
import com.mutsa.delivery.cart.dto.request.CartItemUpdateRequestDto;
import com.mutsa.delivery.cart.dto.response.CartResponseDto;
import com.mutsa.delivery.cart.repository.CartItemRepository;
import com.mutsa.delivery.cart.service.CartService;
import com.mutsa.delivery.global.apiPayload.ApiResponse;
import com.mutsa.delivery.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor

public class CartController {

    private final CartService cartService;

    // 장바구니 조회
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getCart() {
        Long dummyUserId = 1L;
        CartResponseDto responseDto = cartService.getCart(dummyUserId);

        return ResponseEntity.ok(ApiResponse.onSuccess(responseDto));
    }

    // 장바구니 상품 추가
    @PostMapping("/items")
    public ResponseEntity<ApiResponse<?>> addCartItem(
            @Valid @RequestBody CartItemAddRequestDto requestDto) {

        Long cartItemId = cartService.addCartItem(requestDto);
        Map<String, Long> data = Map.of("cartItemId", cartItemId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(GeneralSuccessCode.CREATED, data));
    }

    // 장바구니 상품 수량 변경
    @PatchMapping("/items/{id}")
    public ResponseEntity<ApiResponse<?>> updateCartItemQuantity(
            @PathVariable("id") Long cartItemId,
            @Valid @RequestBody CartItemUpdateRequestDto requestDto) {

        Long dummyUserId = 1L;
        cartService.updateCartItemQuantity(dummyUserId, cartItemId, requestDto.getItemQuantity());

        Map<String, Object> data = Map.of(
                "cartItemId", cartItemId,
                "itemQuantity", requestDto.getItemQuantity()
        );

        return ResponseEntity.ok(ApiResponse.onSuccess(data));
    }

    // 장바구니 상품 삭제
    @DeleteMapping("/items/{id}")
    public ResponseEntity<ApiResponse<?>> deleteCartItem(@PathVariable("id") Long cartItemId) {
        Long dummyUserId = 1L;
        cartService.deleteCartItem(dummyUserId, cartItemId);

        // 반환할 데이터(data)가 없는 삭제 로직은 null을 담아 보냅니다.
        return ResponseEntity.ok(ApiResponse.onSuccess(null));
    }
}
