package com.mutsa.delivery.store.controller;

import com.mutsa.delivery.store.dto.response.StoreResponseDto;
import com.mutsa.delivery.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<List<StoreResponseDto>> getStores(
            @RequestParam(required = false) String category) {
        return ResponseEntity.ok(storeService.getStores(category));
    }
}