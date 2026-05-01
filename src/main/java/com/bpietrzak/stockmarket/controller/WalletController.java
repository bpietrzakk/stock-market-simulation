package com.bpietrzak.stockmarket.controller;

import com.bpietrzak.stockmarket.dto.TradeRequest;
import com.bpietrzak.stockmarket.dto.WalletResponse;
import com.bpietrzak.stockmarket.service.StockTradingService;
import com.bpietrzak.stockmarket.service.WalletService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
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

    // POST: buy or sell a single stock
    @PostMapping("/{wallet_id}/stocks/{stock_name}")
    public ResponseEntity<Void> trade(
            @PathVariable("wallet_id") UUID walletId,
            @PathVariable("stock_name") String stockName,
            @Valid @RequestBody TradeRequest request) {

        // buy or sell - depending on the body
        switch (request.type()) {
            case BUY -> stockTradingService.buy(walletId, stockName);
            case SELL -> stockTradingService.sell(walletId, stockName);
        }

        return ResponseEntity.ok().build();
    }

    // GET: returns current state of the particular wallet
    @GetMapping("/{wallet_id}")
    public ResponseEntity<WalletResponse> getWallet(@PathVariable("wallet_id") UUID walletId) {
        WalletResponse walletResponse = walletService.getWalletState(walletId);
        return ResponseEntity.ok(walletResponse);
    }

    // GET: returns quantity of the specified stock in the specified wallet
    @GetMapping("/{wallet_id}/stocks/{stock_name}")
    public ResponseEntity<Integer> getStockQuantity(
            @PathVariable("wallet_id") UUID walletId,
            @PathVariable("stock_name") String stockName) {
        Integer quantity = walletService.getStockQuantity(walletId, stockName);
        return ResponseEntity.ok(quantity);
    }
}
