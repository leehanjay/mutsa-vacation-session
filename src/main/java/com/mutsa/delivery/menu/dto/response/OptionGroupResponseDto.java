package com.mutsa.delivery.menu.dto.response;

import com.mutsa.delivery.menu.entity.Option;
import com.mutsa.delivery.menu.entity.OptionGroup;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class OptionGroupResponseDto {
    private Long optionGroupId;
    private String groupName;
    private String selectionType;
    private boolean required;
    private List<OptionResponseDto> options;

    // Entity → DTO 변환
    public static OptionGroupResponseDto of(OptionGroup optionGroup, List<Option> options) {
        return OptionGroupResponseDto.builder()
                .optionGroupId(optionGroup.getOptionGroupId())
                .groupName(optionGroup.getGroupName())
                .selectionType(optionGroup.getSelectionType().name())
                .required(optionGroup.isRequired())
                // 응답으로 나갈 때 옵션도 DTO로 나가야하니, 여기서 변환
                .options(options.stream()
                        .map(OptionResponseDto::from)
                        .collect(Collectors.toList()))
                .build();
    }
}