package com.bpietrzak.stockmarket.dto;

import com.bpietrzak.stockmarket.model.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class LogDto {
    private final TransactionType type;

    @JsonProperty("wallet_id")
    private final UUID walletId;

    @JsonProperty("stock_name")
    private final String stockName;

    // CONSTRUCTORS
    public LogDto(TransactionType type, UUID walletId, String stockName) {
        this.type = type;
        this.walletId = walletId;
        this.stockName = stockName;
    }

    // getters
    public TransactionType getType() { return type; }
    public UUID getWalletId() { return walletId; }
    public String getStockName() { return stockName; }
}
