package com.mutsa.delivery.order.dto.response;

import com.mutsa.delivery.order.entity.Order;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResponseDto {

    private Long orderId;
    private Long totalPrice;
    private Long usedCredit;
    private String orderStatus;

    public static OrderResponseDto from(Order order) {
        return OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .totalPrice(order.getTotalPrice())
                .usedCredit(order.getUsedCredit())
                .orderStatus(order.getOrderStatus().name())
                .build();
    }
}
