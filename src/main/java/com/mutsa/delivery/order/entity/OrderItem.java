package com.mutsa.delivery.order.entity;

import com.mutsa.delivery.common.entity.BaseTimeEntity;
import com.mutsa.delivery.menu.entity.Menu;
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
@Table(name = "order_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_store_id", nullable = false)
    private OrderStore orderStore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = true)
    private Menu menu;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @Column(name = "item_price", nullable = false)
    private Long itemPrice;

    @Column(name = "item_quantity", nullable = false)
    private Long itemQuantity;

    @Builder
    public OrderItem(OrderStore orderStore, Menu menu, String menuName, Long itemPrice, Long itemQuantity) {
        this.orderStore = orderStore;
        this.menu = menu;
        this.menuName = menuName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
    }
}
