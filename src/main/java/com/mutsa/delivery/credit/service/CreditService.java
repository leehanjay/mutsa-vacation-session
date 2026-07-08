package com.mutsa.delivery.credit.service;

import com.mutsa.delivery.credit.dto.request.CreditChargeRequestDto;
import com.mutsa.delivery.credit.dto.response.CreditChargeResponseDto;
import com.mutsa.delivery.credit.entity.CreditTransaction;
import com.mutsa.delivery.credit.repository.CreditTransactionRepository;
import com.mutsa.delivery.user.entity.User;
import com.mutsa.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreditService {

    private static final Long CURRENT_USER_ID = 1L; // 로그인 미구현: user_id=1을 현재 로그인 사용자로 간주

    private final UserRepository userRepository;
    private final CreditTransactionRepository creditTransactionRepository;

    @Transactional
    public CreditChargeResponseDto charge(CreditChargeRequestDto request) {
        User user = userRepository.findById(CURRENT_USER_ID)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. userId=" + CURRENT_USER_ID));

        Long balanceAfter = user.getCredit() + request.getAmount(); // 잔액 변화 계산
        user.updateCredit(balanceAfter); // 유저 정보에 업데이트

        // 크레딧 내역 생성
        CreditTransaction creditTransaction = CreditTransaction.charge(user, request.getAmount(), balanceAfter);
        // 그 크레딧 내역을 레포에 저장
        creditTransactionRepository.save(creditTransaction);

        // 그걸 DTO로 변환
        return CreditChargeResponseDto.from(creditTransaction);
    }
}
