package com.bpietrzak.stockmarket.dto;

import com.bpietrzak.stockmarket.model.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record LogDto(
        TransactionType type,
        UUID walletId,
        String stockName
) {}
