package com.mutsa.delivery.order.repository;

import com.mutsa.delivery.order.entity.OrderItemOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemOptionRepository extends JpaRepository<OrderItemOption, Long> {
}
