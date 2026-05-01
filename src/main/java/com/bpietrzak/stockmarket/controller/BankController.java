package com.bpietrzak.stockmarket.controller;

import com.bpietrzak.stockmarket.dto.BankStateResponse;
import com.bpietrzak.stockmarket.dto.BankStateRequest;
import com.bpietrzak.stockmarket.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stocks")
public class BankController {
    private final BankService bankService;

    // constructor
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    // ENDPOINTS

    // POST: set new state of the bank
    @PostMapping
    public ResponseEntity<Void> setBankState(@RequestBody BankStateRequest request) {
        bankService.setBankState(request);

        return ResponseEntity.ok().build();
    }

    // GET: return current state of the bank
    @GetMapping
    public ResponseEntity<BankStateResponse> getBankState() {
        BankStateResponse bankState = bankService.getBankState();

        return ResponseEntity.ok(bankState);
    }
}
