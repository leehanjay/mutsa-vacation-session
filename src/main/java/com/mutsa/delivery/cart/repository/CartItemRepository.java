package com.mutsa.delivery.cart.repository;

import com.mutsa.delivery.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
