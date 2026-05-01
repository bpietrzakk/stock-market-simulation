package com.bpietrzak.stockmarket.dto;

import com.bpietrzak.stockmarket.model.enums.TransactionType;

public record TradeRequest(TransactionType type) {}
