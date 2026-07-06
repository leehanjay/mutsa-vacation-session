package com.mutsa.delivery.order.repository;

import com.mutsa.delivery.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
