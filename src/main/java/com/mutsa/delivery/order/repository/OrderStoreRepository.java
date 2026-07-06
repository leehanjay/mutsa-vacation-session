package com.mutsa.delivery.order.repository;

import com.mutsa.delivery.order.entity.OrderStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStoreRepository extends JpaRepository<OrderStore, Long> {
}
