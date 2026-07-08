package com.mutsa.delivery.store.dto.response;

import com.mutsa.delivery.store.entity.Store;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreResponseDto {
    private Long storeId;
    private String categoryName;
    private String storeName;
    private Double storeRating;
    private String imageUrl;

    // Entity → DTO 변환
    public static StoreResponseDto from(Store store) {
        return StoreResponseDto.builder()
                .storeId(store.getStoreId())
                .categoryName(store.getCategory().getTagName())
                .storeName(store.getStoreName())
                .storeRating(store.getStoreRating())
                .imageUrl(store.getImageUrl())
                .build();
    }
}