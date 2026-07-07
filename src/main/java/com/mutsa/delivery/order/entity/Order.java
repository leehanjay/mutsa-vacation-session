package com.mutsa.delivery.order.entity;

import com.mutsa.delivery.common.entity.BaseTimeEntity;
import com.mutsa.delivery.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total_price", nullable = false)
    private Long totalPrice;

    @Column(name = "used_credit", nullable = false)
    private Long usedCredit;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Builder(access = AccessLevel.PRIVATE)
    private Order(User user, Long totalPrice, Long usedCredit, OrderStatus orderStatus) {
        this.user = user;
        this.totalPrice = totalPrice;
        this.usedCredit = usedCredit;
        this.orderStatus = orderStatus;
    }

    public static Order createNew(User user, Long totalPrice, Long usedCredit) {
        if (user == null) {
            throw new IllegalArgumentException("user must not be null");
        }
        if (totalPrice == null || totalPrice < 0) {
            throw new IllegalArgumentException("totalPrice must not be negative");
        }
        if (usedCredit == null || usedCredit < 0) {
            throw new IllegalArgumentException("usedCredit must not be negative");
        }
        return Order.builder()
                .user(user)
                .totalPrice(totalPrice)
                .usedCredit(usedCredit)
                .orderStatus(OrderStatus.PENDING)
                .build();
    }
}
