package com.bpietrzak.stockmarket.service;

import com.bpietrzak.stockmarket.dto.BankStateRequest;
import com.bpietrzak.stockmarket.dto.StockDto;
import com.bpietrzak.stockmarket.model.BankStock;
import com.bpietrzak.stockmarket.repository.BankStockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.InOrder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankServiceTest {

    @Mock private BankStockRepository bankStockRepository;

    @InjectMocks private BankService service;

    @Test
    void getBankStateShouldReturnAllStocks() {
        // create data and mock
        when(bankStockRepository.findAll()).thenReturn(List.of(
                new BankStock("example", 10),
                new BankStock("other", 5)
        ));

        // call
        var result = service.getBankState();

        // check result
        assertThat(result.stocks()).hasSize(2);
        assertThat(result.stocks().get(0).name()).isEqualTo("example");
        assertThat(result.stocks().get(0).quantity()).isEqualTo(10);
        assertThat(result.stocks().get(1).name()).isEqualTo("other");
        assertThat(result.stocks().get(1).quantity()).isEqualTo(5);
    }

    @Test
    void setBankStateShouldReplaceExistingStocks() {
        // create data and mock
        BankStateRequest request = new BankStateRequest(List.of(new StockDto("newstock", 20)));
        when(bankStockRepository.saveAll(any())).thenReturn(List.of());

        // call
        service.setBankState(request);

        // check delete before save
        InOrder inOrder = inOrder(bankStockRepository);
        inOrder.verify(bankStockRepository).deleteAllInBatch();
        inOrder.verify(bankStockRepository).saveAll(any());

        // check new stocks saved
        ArgumentCaptor<List<BankStock>> captor = ArgumentCaptor.forClass(List.class);
        verify(bankStockRepository).saveAll(captor.capture());
        List<BankStock> saved = captor.getValue();
        assertThat(saved).hasSize(1);
        assertThat(saved.get(0).getName()).isEqualTo("newstock");
        assertThat(saved.get(0).getQuantity()).isEqualTo(20);
    }

    @Test
    void setBankStateShouldHandleEmptyList() {
        // create data and mock
        BankStateRequest request = new BankStateRequest(List.of());
        when(bankStockRepository.saveAll(any())).thenReturn(List.of());

        // call
        service.setBankState(request);

        // check old stocks deleted and nothing saved
        verify(bankStockRepository).deleteAllInBatch();
        ArgumentCaptor<List<BankStock>> captor = ArgumentCaptor.forClass(List.class);
        verify(bankStockRepository).saveAll(captor.capture());
        assertThat(captor.getValue()).isEmpty();
    }
}
