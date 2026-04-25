package com.bpietrzak.stockmarket.repository;

import com.bpietrzak.stockmarket.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
}
