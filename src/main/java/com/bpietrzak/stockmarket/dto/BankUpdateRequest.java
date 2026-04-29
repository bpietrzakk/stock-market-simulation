package com.bpietrzak.stockmarket.dto;

import java.util.List;

public class BankUpdateRequest {
    private final List<StockDto> stocks;

    // CONSTRUCTOR
    public BankUpdateRequest(List<StockDto> stocks) {
        this.stocks = stocks;
    }

    // getters
    public List<StockDto> getStocks() {
        return stocks;
    }
}
