package com.bpietrzak.stockmarket.service;

import com.bpietrzak.stockmarket.exception.NotFoundException;
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
    void buyShouldThrowNotFoundEWhenStockDoesNotExist() {
        // create data and mock
        UUID walletId = UUID.randomUUID();
        String stockName = "dontexist";
        when(bankStockRepository.findByName(stockName)).thenReturn(Optional.empty());

        // call and check if an exception was thrown
        assertThatThrownBy(() -> service.buy(walletId, stockName))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(stockName);
    }
}
