package com.mutsa.delivery.cart.entity;

import com.mutsa.delivery.common.entity.BaseTimeEntity;
import com.mutsa.delivery.menu.entity.Option;
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
@Table(name = "cart_item_options")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItemOption extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_option_id")
    private Long cartItemOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_item_id", nullable = false)
    private CartItem cartItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false)
    private Option option;

    @Builder
    public CartItemOption(CartItem cartItem, Option option) {
        this.cartItem = cartItem;
        this.option = option;
    }
}
