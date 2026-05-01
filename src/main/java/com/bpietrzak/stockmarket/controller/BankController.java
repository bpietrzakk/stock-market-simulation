package com.bpietrzak.stockmarket.controller;

import com.bpietrzak.stockmarket.dto.BankStateRequest;
import com.bpietrzak.stockmarket.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stocks")
public class BankController {
    private final BankService bankService;

    // constructor
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    // ENDPOINTS

    // POST /stocks
    @PostMapping
    public ResponseEntity<Void> setBankState(@RequestBody BankStateRequest request) {
        bankService.setBankState(request);

        return ResponseEntity.ok().build();
    }
    // GET /stocks
}
