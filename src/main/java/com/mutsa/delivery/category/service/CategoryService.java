package com.mutsa.delivery.category.service;

import com.mutsa.delivery.category.dto.response.CategoryResponseDto;
import com.mutsa.delivery.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // final이 붙은 필드에 대한 생성자 자동 생성
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getCategories() {
        // 1. JPA가 날린 DB 쿼리를 통해 온 ROW를 Category 엔터티로 변환
        return categoryRepository.findAll().stream()
                .map(CategoryResponseDto::from) // 2. 스트림으로 바꿔서, 각 엔티티를 DTO로 매핑
                .collect(Collectors.toList()); // 3. 다시 리스트로 모아서 반환
    }
}