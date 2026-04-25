package com.bpietrzak.stockmarket.repository;

import com.bpietrzak.stockmarket.model.WalletStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletStockRepository extends JpaRepository<WalletStock, Long> {
}
