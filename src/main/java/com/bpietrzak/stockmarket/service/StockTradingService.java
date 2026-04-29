package com.bpietrzak.stockmarket.service;

import com.bpietrzak.stockmarket.model.AuditLog;
import com.bpietrzak.stockmarket.model.BankStock;
import com.bpietrzak.stockmarket.model.Wallet;
import com.bpietrzak.stockmarket.model.WalletStock;
import com.bpietrzak.stockmarket.model.enums.TransactionType;
import com.bpietrzak.stockmarket.repository.BankStockRepository;
import com.bpietrzak.stockmarket.repository.WalletRepository;
import com.bpietrzak.stockmarket.repository.WalletStockRepository;
import com.bpietrzak.stockmarket.repository.AuditLogRepository;
import com.bpietrzak.stockmarket.exception.InvalidOperationException;
import com.bpietrzak.stockmarket.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class StockTradingService {
    private final BankStockRepository bankStockRepository;
    private final WalletRepository walletRepository;
    private final WalletStockRepository walletStockRepository;
    private final AuditLogRepository auditLogRepository;

    public StockTradingService(
            BankStockRepository bankStockRepository,
            WalletRepository walletRepository,
            WalletStockRepository walletStockRepository,
            AuditLogRepository auditLogRepository
            ) {
        this.bankStockRepository = bankStockRepository;
        this.walletRepository = walletRepository;
        this.walletStockRepository = walletStockRepository;
        this.auditLogRepository = auditLogRepository;
    }
    // methods buy() and sell()

    @Transactional
    public void buy(UUID walletId, String stockName) {
        // check if stock exist in the bank
        BankStock stock = bankStockRepository.findByName(stockName).orElseThrow(() -> new NotFoundException("Stock not found: " + stockName));

        // check if there are any stocks left to sell
        if (stock.getQuantity() <= 0) {
            throw new InvalidOperationException("Stock " + stockName + " is out of stock");
        }

        // check if wallet exists, if not -> create
        Wallet wallet = walletRepository.findById(walletId).orElseGet(() -> walletRepository.save(new Wallet(walletId)));

        // -- stocks number in bank
        stock.setQuantity(stock.getQuantity() - 1);

        // ++ stocks number in wallet
        WalletStock walletStock = walletStockRepository.findByWalletAndName(wallet, stockName).orElseGet(() -> walletStockRepository.save(new WalletStock(wallet, stockName, 0)));
        walletStock.setQuantity(walletStock.getQuantity() + 1);

        // save auditLog
        auditLogRepository.save(new AuditLog(TransactionType.BUY, walletId, stockName));
    }
}
