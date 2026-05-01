package com.bpietrzak.stockmarket.dto;

import com.bpietrzak.stockmarket.model.enums.TransactionType;
import jakarta.validation.constraints.NotNull;

public record TradeRequest(
        @NotNull(message = "Field 'type' is required and must be 'buy' or 'sell'")
        TransactionType type
) {}
