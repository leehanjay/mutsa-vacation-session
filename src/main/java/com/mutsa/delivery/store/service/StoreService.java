package com.mutsa.delivery.store.service;

import com.mutsa.delivery.menu.dto.response.MenuResponseDto;
import com.mutsa.delivery.menu.dto.response.OptionGroupResponseDto;
import com.mutsa.delivery.menu.entity.Menu;
import com.mutsa.delivery.menu.entity.Option;
import com.mutsa.delivery.menu.entity.OptionGroup;
import com.mutsa.delivery.menu.repository.MenuRepository;
import com.mutsa.delivery.menu.repository.OptionGroupRepository;
import com.mutsa.delivery.menu.repository.OptionRepository;
import com.mutsa.delivery.store.dto.response.StoreDetailResponseDto;
import com.mutsa.delivery.store.dto.response.StoreResponseDto;
import com.mutsa.delivery.store.entity.Store;
import com.mutsa.delivery.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // final 필드들을 매개변수로 받는 생성자를 자동으로 만들어줌
public class StoreService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final OptionRepository optionRepository;

    @Transactional(readOnly = true)
    public List<StoreResponseDto> getStores(String category) {
        String trimmedCategory = category == null ? null : category.trim(); // 앞뒤 공백 제거 후 비교
        List<Store> stores = (trimmedCategory == null || trimmedCategory.isBlank()) // null이면 카테고리 전체를 의미
                ? storeRepository.findAllWithCategory()
                : storeRepository.findByCategory_TagNameWithCategory(trimmedCategory);

        return stores.stream()
                .map(StoreResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StoreDetailResponseDto getStoreDetail(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가게입니다. storeId=" + storeId));

        List<Menu> menus = menuRepository.findByStore(store); // 가게 기준으로 메뉴 찾기
        // 찾은 메뉴들 전체에 대한 옵션그룹을 한 번에 조회 (쿼리 1번). 메뉴가 없으면 빈 IN() 조회를 피하기 위해 바로 빈 리스트 처리
        List<OptionGroup> optionGroups = menus.isEmpty() ? List.of() : optionGroupRepository.findByMenuIn(menus);
        // 그 옵션그룹들에 포함된 옵션을 한 번에 조회 (쿼리 1번). 옵션그룹이 없으면 빈 IN() 조회를 피하기 위해 바로 빈 리스트 처리
        List<Option> options = optionGroups.isEmpty() ? List.of() : optionRepository.findByOptionGroupIn(optionGroups);

        // 아래 MAP은 쿼리 한 번으로 다 불러오다보니, mapping이 안 되어 있어서 mapping 하는 작업
        Map<Long, List<OptionGroup>> optionGroupsByMenuId = optionGroups.stream()
                .collect(Collectors.groupingBy(optionGroup -> optionGroup.getMenu().getMenuId()));
        Map<Long, List<Option>> optionsByGroupId = options.stream()
                .collect(Collectors.groupingBy(option -> option.getOptionGroup().getOptionGroupId()));

        // 위에서 만든 두 MAP을 참고해 최종 DTO로 조립
        List<MenuResponseDto> menuDtos = menus.stream()
                .map(menu -> toMenuResponseDto(menu, optionGroupsByMenuId, optionsByGroupId))
                .collect(Collectors.toList());

        return StoreDetailResponseDto.of(store, menuDtos);
    }

    // 메뉴를 옵션 그룹과 묶기 위한 메서드
    private MenuResponseDto toMenuResponseDto(
            Menu menu,
            Map<Long, List<OptionGroup>> optionGroupsByMenuId,
            Map<Long, List<Option>> optionsByGroupId) {
        List<OptionGroupResponseDto> optionGroupDtos = optionGroupsByMenuId
                .getOrDefault(menu.getMenuId(), List.of())
                .stream()
                .map(optionGroup -> OptionGroupResponseDto.of(
                        optionGroup,
                        optionsByGroupId.getOrDefault(optionGroup.getOptionGroupId(), List.of())))
                .collect(Collectors.toList());
        return MenuResponseDto.of(menu, optionGroupDtos);
    }
}
