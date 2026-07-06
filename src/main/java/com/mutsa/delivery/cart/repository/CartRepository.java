package com.mutsa.delivery.cart.repository;

import com.mutsa.delivery.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
