package com.bpietrzak.stockmarket.integration;

import com.bpietrzak.stockmarket.AbstractIntegrationTest;
import com.bpietrzak.stockmarket.model.BankStock;
import com.bpietrzak.stockmarket.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StockTradingConcurrencyTest extends AbstractIntegrationTest {

    @LocalServerPort int port;
    @Autowired TestRestTemplate rest;
    @Autowired TransactionTemplate tx;
    @Autowired BankStockRepository bankStockRepository;
    @Autowired WalletRepository walletRepository;
    @Autowired WalletStockRepository walletStockRepository;
    @Autowired AuditLogRepository auditLogRepository;

    @BeforeEach
    void cleanDb() {
        auditLogRepository.deleteAll();
        walletStockRepository.deleteAll();
        walletRepository.deleteAll();
        bankStockRepository.deleteAll();
    }

    @Test
    void buyShouldBeThreadSafeWhenBankRunsOut() throws Exception {
        // create data
        bankStockRepository.save(new BankStock("example", 5));
        UUID walletId = UUID.randomUUID();
        String url = "http://localhost:" + port + "/wallets/" + walletId + "/stocks/example";

        // call — 10 threads try to buy at the same time
        int threads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch startGate = new CountDownLatch(1);
        AtomicInteger successes = new AtomicInteger(0);
        AtomicInteger failures = new AtomicInteger(0);

        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                try {
                    startGate.await();
                    var response = rest.postForEntity(url, Map.of("type", "buy"), Void.class);
                    if (response.getStatusCode().value() == 200) {
                        successes.incrementAndGet();
                    } else {
                        failures.incrementAndGet();
                    }
                } catch (Exception e) {
                    failures.incrementAndGet();
                }
            });
        }

        startGate.countDown();
        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);

        // check results
        assertThat(successes.get()).isEqualTo(5);
        assertThat(failures.get()).isEqualTo(5);

        tx.executeWithoutResult(status -> {
            // check stock transfer
            assertThat(bankStockRepository.findByName("example").get().getQuantity()).isEqualTo(0);
            assertThat(walletStockRepository.findAll().getFirst().getQuantity()).isEqualTo(5);

            // check audit log
            assertThat(auditLogRepository.findAll()).hasSize(5);
        });
    }
}