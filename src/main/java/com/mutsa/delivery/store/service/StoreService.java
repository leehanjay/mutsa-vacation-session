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
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final OptionRepository optionRepository;

    @Transactional(readOnly = true)
    public List<StoreResponseDto> getStores(String category) {
        List<Store> stores = (category == null || category.isBlank())
                ? storeRepository.findAll()
                : storeRepository.findByCategory_TagName(category);

        return stores.stream()
                .map(StoreResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StoreDetailResponseDto getStoreDetail(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가게입니다. storeId=" + storeId));

        List<Menu> menus = menuRepository.findByStore(store);
        List<OptionGroup> optionGroups = optionGroupRepository.findByMenuIn(menus);
        List<Option> options = optionRepository.findByOptionGroupIn(optionGroups);

        Map<Long, List<OptionGroup>> optionGroupsByMenuId = optionGroups.stream()
                .collect(Collectors.groupingBy(optionGroup -> optionGroup.getMenu().getMenuId()));
        Map<Long, List<Option>> optionsByGroupId = options.stream()
                .collect(Collectors.groupingBy(option -> option.getOptionGroup().getOptionGroupId()));

        List<MenuResponseDto> menuDtos = menus.stream()
                .map(menu -> toMenuResponseDto(menu, optionGroupsByMenuId, optionsByGroupId))
                .collect(Collectors.toList());

        return StoreDetailResponseDto.of(store, menuDtos);
    }

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
