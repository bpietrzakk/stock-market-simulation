package com.bpietrzak.stockmarket.dto;

import java.util.List;

public record BankStateRequest(List<StockDto> stocks) {}
