package com.mutsa.delivery.cart.repository;

import com.mutsa.delivery.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
//변경됨
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("select c from Cart c where c.user.userId = :userId")
    Optional<Cart> findByUserId(@Param("userId") Long userId);
}
