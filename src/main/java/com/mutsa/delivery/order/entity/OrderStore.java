package com.mutsa.delivery.order.entity;

import com.mutsa.delivery.common.entity.BaseTimeEntity;
import com.mutsa.delivery.store.entity.Store;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_stores")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderStore extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_store_id")
    private Long orderStoreId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = true)
    private Store store;

    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Builder(access = AccessLevel.PRIVATE)
    private OrderStore(Order order, Store store, String storeName) {
        this.order = order;
        this.store = store;
        this.storeName = storeName;
    }

    public static OrderStore createNew(Order order, Store store, String storeName) {
        if (order == null) {
            throw new IllegalArgumentException("order must not be null");
        }
        if (storeName == null || storeName.isBlank()) {
            throw new IllegalArgumentException("storeName must not be blank");
        }
        return OrderStore.builder()
                .order(order)
                .store(store)
                .storeName(storeName)
                .build();
    }
}
