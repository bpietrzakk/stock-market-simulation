package com.bpietrzak.stockmarket.dto;

import java.util.List;
import java.util.UUID;

public class WalletResponse {
    private final UUID id;
    private final List<StockDto> stocks;

    // CONSTRUCTORS
    public WalletResponse(UUID id, List<StockDto> stocks) {
        this.id = id;
        this.stocks = stocks;
    }

    // GETTERS
    public UUID getId() { return id; }
    public List<StockDto> getStocks() { return stocks; }
}
