package com.bpietrzak.stockmarket.service;

import com.bpietrzak.stockmarket.exception.InvalidOperationException;
import com.bpietrzak.stockmarket.exception.NotFoundException;
import com.bpietrzak.stockmarket.model.BankStock;
import com.bpietrzak.stockmarket.model.Wallet;
import com.bpietrzak.stockmarket.model.WalletStock;
import com.bpietrzak.stockmarket.repository.AuditLogRepository;
import com.bpietrzak.stockmarket.repository.BankStockRepository;
import com.bpietrzak.stockmarket.repository.WalletRepository;
import com.bpietrzak.stockmarket.repository.WalletStockRepository;
import org.hibernate.annotations.NotFound;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockTradingServiceTest {

    // mock data
    @Mock private BankStockRepository bankStockRepository;
    @Mock private WalletRepository walletRepository;
    @Mock private WalletStockRepository walletStockRepository;
    @Mock private AuditLogRepository auditLogRepository;

    // create mock service
    @InjectMocks private StockTradingService service;

    @Test
    void buyShouldThrowNotFoundWhenStockDoesNotExist() {
        // create data and mock
        UUID walletId = UUID.randomUUID();
        String stockName = "DontExist";
        when(bankStockRepository.findByName(stockName)).thenReturn(Optional.empty());

        // call and check if an exception was thrown
        assertThatThrownBy(() -> service.buy(walletId, stockName))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(stockName);
    }
    @Test
    void buyShouldThrowInvalidOperationWhenBankHasNoStocks() {
        // create data and mock
        UUID walletId = UUID.randomUUID();
        String stockName = "ZeroInBank";
        BankStock emptyStock = new BankStock(stockName, 0);
        when(bankStockRepository.findByName(stockName)).thenReturn(Optional.of(emptyStock));

        // call and check if an exception was thrown
        assertThatThrownBy(() -> service.buy(walletId, stockName))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessageContaining(stockName);
    }

    @Test
    void sellShouldThrowNotFoundWhenStockDoesNotExist() {
        // create data and mock
        UUID walletId = UUID.randomUUID();
        String stockName = "DontExist";
        when(bankStockRepository.findByName(stockName)).thenReturn(Optional.empty());

        // call and check if an exception was thrown
        assertThatThrownBy(() -> service.sell(walletId, stockName))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(stockName);
    }

    @Test
    void sellShouldThrowInvalidOperationWhenWalletHasNoStock() {
        // create data and mock
        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet(walletId);
        String stockName = "NotInWalletStock";
        BankStock bankStock = new BankStock(stockName, 1);
        when(bankStockRepository.findByName(stockName)).thenReturn(Optional.of(bankStock));
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        when(walletStockRepository.findByWalletAndName(wallet, stockName)).thenReturn(Optional.empty());

        // call and check if an exception was thrown
        assertThatThrownBy(() -> service.sell(walletId, stockName))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessageContaining(stockName);
    }

    @Test
    void sellShouldThrowInvalidOperationWhenWalletHasNoStocks() {
        // create data and mock
        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet(walletId);
        String stockName = "NotInWalletStock";
        BankStock bankStock = new BankStock(stockName, 1);
        WalletStock walletStock = new WalletStock(wallet, stockName, 0);
        when(bankStockRepository.findByName(stockName)).thenReturn(Optional.of(bankStock));
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        when(walletStockRepository.findByWalletAndName(wallet, stockName)).thenReturn(Optional.of(walletStock));

        // call and check if an exception was thrown
        assertThatThrownBy(() -> service.sell(walletId, stockName))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessageContaining(stockName);
    }
}
