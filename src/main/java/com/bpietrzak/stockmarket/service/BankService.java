package com.bpietrzak.stockmarket.service;

import com.bpietrzak.stockmarket.dto.BankResponse;
import com.bpietrzak.stockmarket.dto.BankUpdateRequest;
import com.bpietrzak.stockmarket.exception.InvalidOperationException;
import com.bpietrzak.stockmarket.model.BankStock;
import com.bpietrzak.stockmarket.repository.BankStockRepository;
import com.bpietrzak.stockmarket.dto.StockDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BankService {
    private final BankStockRepository bankStockRepository;

    // CONSTRUCTOR
    public BankService (BankStockRepository bankStockRepository) {
        this.bankStockRepository = bankStockRepository;
    }

    // methods
    @Transactional(readOnly = true)
    public BankResponse getBankState() {
        List<StockDto> stocks = bankStockRepository.findAll().stream()
                .map(bs -> new StockDto(bs.getName(), bs.getQuantity()))
                .toList();
        return new BankResponse(stocks);
    }

    @Transactional
    public void setBankState(BankUpdateRequest bankUpdateRequest) {
        // check for duplicate
        Set<String> uniqueNames = bankUpdateRequest.stocks().stream()
                .map(StockDto::name)
                .collect(Collectors.toSet());

        if (uniqueNames.size() != bankUpdateRequest.stocks().size()) {
            throw new InvalidOperationException("Duplicate stock names in request");
        }
        bankStockRepository.deleteAllInBatch();

        List<BankStock> stocks = bankUpdateRequest.stocks().stream()
                .map(dto -> new BankStock(dto.name(), dto.quantity()))
                .toList();

        bankStockRepository.saveAll(stocks);
    }
}
