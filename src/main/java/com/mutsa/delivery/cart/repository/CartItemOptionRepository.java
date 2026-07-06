package com.mutsa.delivery.cart.repository;

import com.mutsa.delivery.cart.entity.CartItemOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemOptionRepository extends JpaRepository<CartItemOption, Long> {
}
