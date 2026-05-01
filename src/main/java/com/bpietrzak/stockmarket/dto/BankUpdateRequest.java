package com.bpietrzak.stockmarket.dto;

import java.util.List;

public record BankUpdateRequest(List<StockDto> stocks) {}
