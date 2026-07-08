package com.mutsa.delivery.store.repository;

import com.mutsa.delivery.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByCategory_TagName(String tagName);
}
