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

    // menus는 Menu 엔티티가 아니라 이미 완성된 DTO 리스트를 받음 — 옵션그룹 조회가 필요해서 Service에서 미리 조립된 걸 넘겨받는 것
    public static StoreDetailResponseDto of(Store store, List<MenuResponseDto> menus) {
        return StoreDetailResponseDto.builder()
                .storeId(store.getStoreId())
                // Category는 Store가 FK로 직접 참조하고 있어서(@ManyToOne), Repository 없이 바로 접근 가능
                .categoryName(store.getCategory().getTagName())
                .storeName(store.getStoreName())
                .storeRating(store.getStoreRating())
                .imageUrl(store.getImageUrl())
                .menus(menus)
                .build();
    }
}