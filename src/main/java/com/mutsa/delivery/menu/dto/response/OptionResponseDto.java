package com.mutsa.delivery.menu.dto.response;

import com.mutsa.delivery.menu.entity.Option;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OptionResponseDto {
    private Long optionId;
    private String optionName;
    private Long extraPrice;

    // Entity → DTO 변환
    public static OptionResponseDto from(Option option) {
        return OptionResponseDto.builder()
                .optionId(option.getOptionId())
                .optionName(option.getOptionName())
                .extraPrice(option.getExtraPrice())
                .build();
    }
}
