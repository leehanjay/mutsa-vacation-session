package com.mutsa.delivery.store.dto.response;

import com.mutsa.delivery.menu.dto.response.MenuResponseDto;
import com.mutsa.delivery.store.entity.Store;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StoreDetailResponseDto {
    private Long storeId;
    private String categoryName;
    private String storeName;
    private Double storeRating;
    private String imageUrl;
    private List<MenuResponseDto> menus;

    public static StoreDetailResponseDto of(Store store, List<MenuResponseDto> menus) {
        return StoreDetailResponseDto.builder()
                .storeId(store.getStoreId())
                .categoryName(store.getCategory().getTagName())
                .storeName(store.getStoreName())
                .storeRating(store.getStoreRating())
                .imageUrl(store.getImageUrl())
                .menus(menus)
                .build();
    }
}