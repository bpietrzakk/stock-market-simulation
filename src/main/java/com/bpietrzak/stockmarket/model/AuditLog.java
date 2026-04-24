package com.bpietrzak.stockmarket.model;

import com.bpietrzak.stockmarket.model.enums.TransactionType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="logs")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private UUID walletId;

    @Column(nullable = false)
    private String stockName;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // CONSTRUCTORS
    public AuditLog() {}

    public AuditLog(TransactionType type, UUID walletId, String stockName) {
        this.type = type;
        this.walletId = walletId;
        this.stockName = stockName;
    }

    // GETTERS
    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public String getStockName() {
        return stockName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
