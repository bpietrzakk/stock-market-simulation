package com.bpietrzak.stockmarket.controller;

import com.bpietrzak.stockmarket.dto.TradeRequest;
import com.bpietrzak.stockmarket.model.enums.TransactionType;
import com.bpietrzak.stockmarket.service.StockTradingService;
import com.bpietrzak.stockmarket.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/wallets")
public class WalletController {
    private final WalletService walletService;
    private final StockTradingService stockTradingService;

    // CONSTRUCTOR
    public WalletController(WalletService walletService, StockTradingService stockTradingService) {
        this.walletService = walletService;
        this.stockTradingService = stockTradingService;
    }

    // ENDPOINTS
    // POST /wallets/{wallet_id}/stocks/{stock_name}
    @PostMapping("/{wallet_id}/stocks/{stock_name}")
    public ResponseEntity<Void> trade(
            @PathVariable("wallet_id") UUID walletId,
            @PathVariable("stock_name") String stockName,
            @RequestBody TradeRequest request) {

        // buy or sell - depending on the body
        switch (request.type()) {
            case BUY -> stockTradingService.buy(walletId, stockName);
            case SELL -> stockTradingService.sell(walletId, stockName);
        }

        return ResponseEntity.ok().build();
    }


    // GET /wallets/{wallet_id}

    // GET /wallets/{wallet_id}/stocks/{stock_name}
}
