package com.mutsa.delivery.cart.controller;

import com.mutsa.delivery.cart.dto.request.CartItemAddRequestDto;
import com.mutsa.delivery.cart.dto.request.CartItemUpdateRequestDto;
import com.mutsa.delivery.cart.dto.response.CartResponseDto;
import com.mutsa.delivery.cart.repository.CartItemRepository;
import com.mutsa.delivery.cart.service.CartService;
import com.mutsa.delivery.global.apiPayload.ApiResponse;
import com.mutsa.delivery.global.apiPayload.code.GeneralSuccessCode;
import com.mutsa.delivery.global.security.JwtUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<ApiResponse<CartResponseDto>> getCart(@AuthenticationPrincipal JwtUserPrincipal principal) {
        // 인증된 유저의 ID를 직접 전달
        CartResponseDto responseDto = cartService.getCart(principal.userId());
        return ResponseEntity.ok(ApiResponse.onSuccess(responseDto));
    }

    @PostMapping("/items")
    public ResponseEntity<ApiResponse<Map<String, Long>>> addCartItem(
            @AuthenticationPrincipal JwtUserPrincipal principal,
            @Valid @RequestBody CartItemAddRequestDto requestDto) {

        // 서비스단으로 로그인된 유저 ID를 넘겨주도록 수정
        Long cartItemId = cartService.addCartItem(principal.userId(), requestDto);
        Map<String, Long> data = Map.of("cartItemId", cartItemId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(GeneralSuccessCode.CREATED, data));
    }

    @PatchMapping("/items/{id}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateCartItemQuantity(
            @PathVariable("id") Long cartItemId,
            @AuthenticationPrincipal JwtUserPrincipal principal,
            @Valid @RequestBody CartItemUpdateRequestDto requestDto) {

        cartService.updateCartItemQuantity(principal.userId(), cartItemId, requestDto.getItemQuantity());

        Map<String, Object> data = Map.of(
                "cartItemId", cartItemId,
                "itemQuantity", requestDto.getItemQuantity()
        );

        return ResponseEntity.ok(ApiResponse.onSuccess(data));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCartItem(
            @PathVariable("id") Long cartItemId,
            @AuthenticationPrincipal JwtUserPrincipal principal) {

        cartService.deleteCartItem(principal.userId(), cartItemId);
        return ResponseEntity.ok(ApiResponse.onSuccess(null));
    }
}