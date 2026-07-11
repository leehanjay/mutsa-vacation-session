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

    @GetMapping
    public ResponseEntity<ApiResponse<CartResponseDto>> getCart() { // 구체 타입 명시
        Long dummyUserId = 1L;
        CartResponseDto responseDto = cartService.getCart(dummyUserId);
        return ResponseEntity.ok(ApiResponse.onSuccess(responseDto));
    }

    @PostMapping("/items")
    public ResponseEntity<ApiResponse<Map<String, Long>>> addCartItem( // 구체 타입 명시
                                                                       @Valid @RequestBody CartItemAddRequestDto requestDto) {

        Long cartItemId = cartService.addCartItem(requestDto);
        Map<String, Long> data = Map.of("cartItemId", cartItemId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(GeneralSuccessCode.CREATED, data));
    }

    @PatchMapping("/items/{id}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateCartItemQuantity( // 구체 타입 명시
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

    @DeleteMapping("/items/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCartItem(@PathVariable("id") Long cartItemId) { // Void 명시
        Long dummyUserId = 1L;
        cartService.deleteCartItem(dummyUserId, cartItemId);
        return ResponseEntity.ok(ApiResponse.onSuccess(null));
    }
}
