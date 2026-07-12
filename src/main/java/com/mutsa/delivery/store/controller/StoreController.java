package com.mutsa.delivery.store.controller;

import com.mutsa.delivery.global.apiPayload.ApiResponse;
import com.mutsa.delivery.store.dto.response.StoreDetailResponseDto;
import com.mutsa.delivery.store.dto.response.StoreResponseDto;
import com.mutsa.delivery.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // 이 클래스가 API요청을 받을거임
@RequiredArgsConstructor // final 필드들을 매개변수로 받는 생성자를 자동으로 만들어줌
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<StoreResponseDto>>> getStores(
            // "/stores?category={카테고리명}" 요청의 쿼리파라미터를 받기 위한 어노테이션
            @RequestParam(required = false) String category) {
        return ResponseEntity.ok(ApiResponse.onSuccess(storeService.getStores(category)));
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<ApiResponse<StoreDetailResponseDto>> getStoreDetail(@PathVariable Long storeId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(storeService.getStoreDetail(storeId)));
    }
}
