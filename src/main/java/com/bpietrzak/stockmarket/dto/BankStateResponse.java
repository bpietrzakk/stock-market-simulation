package com.bpietrzak.stockmarket.dto;

import java.util.List;

public record BankStateResponse(List<StockDto> stocks) {}
