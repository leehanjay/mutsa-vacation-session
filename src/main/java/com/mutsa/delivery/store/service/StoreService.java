package com.mutsa.delivery.store.service;

import com.mutsa.delivery.store.dto.response.StoreResponseDto;
import com.mutsa.delivery.store.entity.Store;
import com.mutsa.delivery.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public List<StoreResponseDto> getStores(String category) {
        List<Store> stores = (category == null || category.isBlank())
                ? storeRepository.findAll()
                : storeRepository.findByCategory_TagName(category);

        return stores.stream()
                .map(StoreResponseDto::from)
                .collect(Collectors.toList());
    }
}
