package com.bpietrzak.stockmarket.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {
    BUY,
    SELL;

    @JsonValue
    public String toLowerCase() {
        return name().toLowerCase();
    }
}
