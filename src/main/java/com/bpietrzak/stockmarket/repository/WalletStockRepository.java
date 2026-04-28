package com.bpietrzak.stockmarket.repository;

import com.bpietrzak.stockmarket.model.Wallet;
import com.bpietrzak.stockmarket.model.WalletStock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface WalletStockRepository extends JpaRepository<WalletStock, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<WalletStock> findByWalletAndName(Wallet wallet, String stockName);
}
