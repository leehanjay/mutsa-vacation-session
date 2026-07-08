package com.mutsa.delivery.category.dto.response;

import com.mutsa.delivery.category.entity.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponseDto {
    private Long categoryId;
    private String tagName;

    // Entity → DTO 변환
    public static CategoryResponseDto from(Category category) {
        return CategoryResponseDto.builder()
                .categoryId(category.getCategoryId())
                .tagName(category.getTagName())
                .build();
    }
}