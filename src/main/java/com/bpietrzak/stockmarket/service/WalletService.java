package com.bpietrzak.stockmarket.service;

import com.bpietrzak.stockmarket.model.Wallet;
import com.bpietrzak.stockmarket.model.WalletStock;
import com.bpietrzak.stockmarket.repository.WalletRepository;
import com.bpietrzak.stockmarket.repository.WalletStockRepository;
import com.bpietrzak.stockmarket.dto.WalletResponse;
import com.bpietrzak.stockmarket.dto.StockDto;
import com.bpietrzak.stockmarket.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final WalletStockRepository walletStockRepository;

    // CONSTRUCTOR
    public WalletService(
            WalletRepository walletRepository,
            WalletStockRepository walletStockRepository
            ) {
        this.walletRepository = walletRepository;
        this.walletStockRepository = walletStockRepository;
    }

    // methods
    @Transactional(readOnly = true)
    public WalletResponse getWalletState(UUID walletId) {
        // check if wallet exist
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new NotFoundException("Wallet not found: " + walletId));

        // create a list of stocks
        List<StockDto> stocks = walletStockRepository.findByWallet(wallet).stream()
                .map(ws -> new StockDto(ws.getName(), ws.getQuantity()))
                .toList();

        // return response
        return new WalletResponse(wallet.getId(), stocks);
    }

    @Transactional(readOnly = true)
    public int getStockQuantity(UUID walletId, String stockName) {
        // check if wallet exist
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new NotFoundException("Wallet not found: " + walletId));

        // return 0 if wallet doesn't have stock
        return walletStockRepository.findByWalletAndName(wallet, stockName)
                .map(WalletStock::getQuantity)
                .orElse(0);
    }
}
