package com.bpietrzak.stockmarket.repository;

import com.bpietrzak.stockmarket.model.BankStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankStockRepository extends JpaRepository<BankStock, Long> {
}
