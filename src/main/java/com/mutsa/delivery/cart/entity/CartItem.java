package com.mutsa.delivery.cart.entity;

import com.mutsa.delivery.common.entity.BaseTimeEntity;
import com.mutsa.delivery.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(name = "item_quantity", nullable = false)
    private Long itemQuantity;

    @OneToMany(mappedBy = "cartItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemOption> cartItemOptions = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    private CartItem(Cart cart, Menu menu, Long itemQuantity) {
        this.cart = cart;
        this.menu = menu;
        this.itemQuantity = itemQuantity;
    }

    public static CartItem createNew(Cart cart, Menu menu, Long itemQuantity) {
        if (cart == null) {
            throw new IllegalArgumentException("cart must not be null");
        }
        if (menu == null) {
            throw new IllegalArgumentException("menu must not be null");
        }
        if (itemQuantity == null || itemQuantity <= 0) {
            throw new IllegalArgumentException("itemQuantity must be positive");
        }
        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .menu(menu)
                .itemQuantity(itemQuantity)
                .build();
        cart.getCartItems().add(cartItem);
        return cartItem;
    }
}
