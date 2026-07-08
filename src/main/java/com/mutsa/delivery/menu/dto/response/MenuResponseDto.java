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

    public static MenuResponseDto of(Menu menu, List<OptionGroupResponseDto> optionGroups) {
        return MenuResponseDto.builder()
                .menuId(menu.getMenuId())
                .menuName(menu.getMenuName())
                .menuPrice(menu.getMenuPrice())
                .optionGroups(optionGroups)
                .build();
    }
}