package com.mutsa.delivery.menu.dto.response;

import com.mutsa.delivery.menu.entity.Menu;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MenuResponseDto {
    private Long menuId;
    private String menuName;
    private Long menuPrice;
    private List<OptionGroupResponseDto> optionGroups;

    // Entity → DTO 변환
    // Service에서 미리 조립된 DTO 리스트를 넘겨 받음
    public static MenuResponseDto of(Menu menu, List<OptionGroupResponseDto> optionGroups) {
        return MenuResponseDto.builder()
                .menuId(menu.getMenuId())
                .menuName(menu.getMenuName())
                .menuPrice(menu.getMenuPrice())
                .optionGroups(optionGroups)
                .build();
    }
}