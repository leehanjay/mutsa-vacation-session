package com.mutsa.delivery.category.controller;

import com.mutsa.delivery.category.dto.response.CategoryResponseDto;
import com.mutsa.delivery.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // API 관련 요청이 오는 걸 받음
@RequiredArgsConstructor // final이 붙은 필드에 대한 생성자 자동 생성
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }
}