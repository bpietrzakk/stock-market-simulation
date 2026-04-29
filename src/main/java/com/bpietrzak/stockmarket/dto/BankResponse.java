package com.bpietrzak.stockmarket.dto;

import java.util.List;

public class BankResponse {
    private final List<StockDto> stocks;

    // CONSTRUCTOR
    public BankResponse(List<StockDto> stocks) {
        this.stocks = stocks;
    }

    // getters
    public List<StockDto> getStocks() {
        return stocks;
    }
}
