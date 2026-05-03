package com.bpietrzak.stockmarket.integration;

import com.bpietrzak.stockmarket.AbstractIntegrationTest;
import com.bpietrzak.stockmarket.model.BankStock;
import com.bpietrzak.stockmarket.repository.AuditLogRepository;
import com.bpietrzak.stockmarket.repository.BankStockRepository;
import com.bpietrzak.stockmarket.repository.WalletRepository;
import com.bpietrzak.stockmarket.repository.WalletStockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StockTradingIntegrationTest  extends AbstractIntegrationTest {
    @LocalServerPort int port;
    @Autowired TestRestTemplate rest;
    @Autowired BankStockRepository bankStockRepository;
    @Autowired WalletRepository walletRepository;
    @Autowired WalletStockRepository walletStockRepository;
    @Autowired AuditLogRepository auditLogRepository;
    @Autowired TransactionTemplate tx;

    @BeforeEach
    void cleanDb() {
        auditLogRepository.deleteAll();
        walletRepository.deleteAll();
        walletStockRepository.deleteAll();
        bankStockRepository.deleteAll();
    }

    // Integration tests
    @Test
    void shouldBuyStockEndToEnd() {
        // create data
        bankStockRepository.save(new BankStock("example", 10));
        UUID walletId = UUID.randomUUID();

        // call
        String url = "http://localhost:" + port + "/wallets/" + walletId + "/stocks/example";
        var response = rest.postForEntity(url, Map.of("type", "buy"), Void.class);

        // check status
        assertThat(response.getStatusCode().value()).isEqualTo(200);

        tx.executeWithoutResult(status -> {
            // check stock transfer
            assertThat(bankStockRepository.findByName("example").get().getQuantity()).isEqualTo(9);
            assertThat(walletStockRepository.findAll().getFirst().getQuantity()).isEqualTo(1);

            // check audit log
            assertThat(auditLogRepository.findAll()).hasSize(1);
        });
    }
}
