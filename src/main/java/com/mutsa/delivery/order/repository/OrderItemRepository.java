package com.mutsa.delivery.order.repository;

import com.mutsa.delivery.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
