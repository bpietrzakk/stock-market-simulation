package com.bpietrzak.stockmarket.dto;

import java.util.List;
import java.util.UUID;

public record WalletResponse(UUID id, List<StockDto> stocks) {}
