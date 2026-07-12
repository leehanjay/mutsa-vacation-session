package com.mutsa.delivery.store.repository;

import com.mutsa.delivery.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    // category를 fetch join으로 함께 조회해 목록 조회 시 N+1 쿼리 발생을 막음
    @Query("select s from Store s join fetch s.category")
    List<Store> findAllWithCategory();

    @Query("select s from Store s join fetch s.category c where c.tagName = :tagName")
    List<Store> findByCategory_TagNameWithCategory(@Param("tagName") String tagName);
}
