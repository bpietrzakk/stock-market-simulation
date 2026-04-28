package com.bpietrzak.stockmarket.repository;

import com.bpietrzak.stockmarket.model.BankStock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface BankStockRepository extends JpaRepository<BankStock, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<BankStock> findByName(String name);
}
