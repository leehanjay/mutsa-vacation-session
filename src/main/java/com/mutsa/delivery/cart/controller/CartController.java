package com.mutsa.delivery.cart.controller;

import com.mutsa.delivery.cart.dto.request.CartItemAddRequestDto;
import com.mutsa.delivery.cart.dto.request.CartItemUpdateRequestDto;
import com.mutsa.delivery.cart.dto.response.CartResponseDto;
import com.mutsa.delivery.cart.repository.CartItemRepository;
import com.mutsa.delivery.cart.service.CartService;
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
    private final CartItemRepository cartItemRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getCart() {
        Long dummyUserId = 1L; // 로그인 대용 더미 유저 ID 고정
        CartResponseDto responseDto = cartService.getCart(dummyUserId);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "장바구니 조회가 완료되었습니다.",
                "data", responseDto
        ));
    }

    @PostMapping("/items")
    public ResponseEntity<Map<String, Object>> addCartItem(
            @Valid @RequestBody CartItemAddRequestDto requestDto) {

        Long cartItemId = cartService.addCartItem(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "success", true,
                "message", "장바구니에 상품이 성공적으로 담겼습니다.",
                "data", Map.of("cartItemId", cartItemId)
        ));
    }

    @PatchMapping("/items/{id}")
    public ResponseEntity<Map<String, Object>> updateCartItemQuantity(
            @PathVariable("id") Long cartItemId,
            @Valid @RequestBody CartItemUpdateRequestDto requestDto) {

        Long dummyUserId = 1L;

        cartService.updateCartItemQuantity(dummyUserId, cartItemId, requestDto.getItemQuantity());


        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "상품 수량이 성공적으로 변경되었습니다.",
                "data", Map.of(
                        "cartItemId", cartItemId,
                        "itemQuantity", requestDto.getItemQuantity()
                )
        ));
    }
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Map<String, Object>> deleteCartItem(@PathVariable("id") Long cartItemId){
        Long dummyUserId = 1L;

        cartService.deleteCartItem(dummyUserId,cartItemId);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "장바구니에서 상품이 성공적으로 삭제되었습니다."
        ));
    }
}
