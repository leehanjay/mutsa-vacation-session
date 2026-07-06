package com.mutsa.delivery.credit.entity;

import com.mutsa.delivery.common.entity.BaseTimeEntity;
import com.mutsa.delivery.order.entity.Order;
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
@Table(name = "credit_transactions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreditTransaction extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_transaction_id")
    private Long creditTransactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = true)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CreditTransactionType type;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "balance_after", nullable = false)
    private Long balanceAfter;

    @Builder
    public CreditTransaction(User user, Order order, CreditTransactionType type, Long amount, Long balanceAfter) {
        this.user = user;
        this.order = order;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }
}
