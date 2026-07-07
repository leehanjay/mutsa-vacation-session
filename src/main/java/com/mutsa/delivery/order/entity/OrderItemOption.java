package com.mutsa.delivery.order.entity;

import com.mutsa.delivery.cart.entity.CartItemOption;
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
@Table(name = "order_item_options")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemOption extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_option_id")
    private Long orderItemOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id", nullable = false)
    private OrderItem orderItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = true)
    private Option option;

    @Column(name = "option_name", nullable = false)
    private String optionName;

    @Column(name = "option_price", nullable = false)
    private Long optionPrice;

    @Builder(access = AccessLevel.PRIVATE)
    private OrderItemOption(OrderItem orderItem, Option option, String optionName, Long optionPrice) {
        this.orderItem = orderItem;
        this.option = option;
        this.optionName = optionName;
        this.optionPrice = optionPrice;
    }

    public static OrderItemOption fromCartItemOption(OrderItem orderItem, CartItemOption cartItemOption) {
        if (orderItem == null) {
            throw new IllegalArgumentException("orderItem must not be null");
        }
        if (cartItemOption == null) {
            throw new IllegalArgumentException("cartItemOption must not be null");
        }
        Option option = cartItemOption.getOption();
        if (option == null) {
            throw new IllegalArgumentException("cartItemOption.option must not be null");
        }
        return OrderItemOption.builder()
                .orderItem(orderItem)
                .option(option)
                .optionName(option.getOptionName())
                .optionPrice(option.getExtraPrice())
                .build();
    }
}
