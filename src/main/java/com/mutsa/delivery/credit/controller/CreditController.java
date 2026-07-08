package com.mutsa.delivery.credit.controller;

import com.mutsa.delivery.credit.dto.request.CreditChargeRequestDto;
import com.mutsa.delivery.credit.dto.response.CreditChargeResponseDto;
import com.mutsa.delivery.credit.service.CreditService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/credits")
public class CreditController {

    private final CreditService creditService;

    @PostMapping("/charge")
    public ResponseEntity<CreditChargeResponseDto> charge(@Valid @RequestBody CreditChargeRequestDto request) {
        return ResponseEntity.ok(creditService.charge(request));
    }
}
