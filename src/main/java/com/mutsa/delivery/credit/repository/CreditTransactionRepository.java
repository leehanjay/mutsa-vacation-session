package com.mutsa.delivery.credit.repository;

import com.mutsa.delivery.credit.entity.CreditTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditTransactionRepository extends JpaRepository<CreditTransaction, Long> {
}
