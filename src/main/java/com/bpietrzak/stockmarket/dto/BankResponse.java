package com.bpietrzak.stockmarket.dto;

import java.util.List;

public record BankResponse(List<StockDto> stocks) {}
