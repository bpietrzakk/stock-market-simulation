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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public void buy(UUID walletID, String stockName) {
        // check if stock exist (404)
        BankStock stock = bankStockRepository.findByName(stockName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "stock not found"));

        // check if stock is in the bank (>0) (400)
        if (stock.getQuantity() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "there is no stock in bank");
        }

        // check if wallet exists, if not -> create
        Wallet wallet = walletRepository.findById(walletID).orElseGet(() -> walletRepository.save(new Wallet()));

        // -- stocks number in bank
        stock.setQuantity(stock.getQuantity() - 1);
        // ++ stocks number in wallet
        WalletStock walletStock = walletStockRepository.findByWalletAndName(wallet, stockName).orElseGet(() -> new WalletStock(wallet, stockName, 0));
        walletStock.setQuantity(walletStock.getQuantity() + 1);
        walletStockRepository.save(walletStock);
        // save auditLog
        auditLogRepository.save(new AuditLog(TransactionType.BUY, walletID, stockName));
    }
}
