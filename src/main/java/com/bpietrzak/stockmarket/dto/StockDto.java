package com.bpietrzak.stockmarket.dto;

public class StockDto {
    private final String name;
    private final int quantity;

    // CONSTRUCTORS
    public StockDto(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    // GETTERS
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
}
