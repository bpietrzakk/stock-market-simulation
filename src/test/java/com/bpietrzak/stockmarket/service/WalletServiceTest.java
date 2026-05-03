package com.bpietrzak.stockmarket.service;

import com.bpietrzak.stockmarket.exception.NotFoundException;
import com.bpietrzak.stockmarket.model.Wallet;
import com.bpietrzak.stockmarket.model.WalletStock;
import com.bpietrzak.stockmarket.repository.WalletRepository;
import com.bpietrzak.stockmarket.repository.WalletStockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock private WalletRepository walletRepository;
    @Mock private WalletStockRepository walletStockRepository;

    @InjectMocks private WalletService service;

    @Test
    void getWalletStateShouldThrowNotFoundWhenWalletDoesNotExist() {
        // create data and mock
        UUID walletId = UUID.randomUUID();
        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

        // call and check if an exception was thrown
        assertThatThrownBy(() -> service.getWalletState(walletId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(walletId.toString());
    }

    @Test
    void getWalletStateShouldReturnStocksWhenWalletExists() {
        // create data and mock
        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet(walletId);
        WalletStock walletStock = new WalletStock(wallet, "example", 3);
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        when(walletStockRepository.findByWallet(wallet)).thenReturn(List.of(walletStock));

        // call
        var result = service.getWalletState(walletId);

        // check result
        assertThat(result.id()).isEqualTo(walletId);
        assertThat(result.stocks()).hasSize(1);
        assertThat(result.stocks().getFirst().name()).isEqualTo("example");
        assertThat(result.stocks().getFirst().quantity()).isEqualTo(3);
    }

    @Test
    void getStockQuantityShouldReturnZeroWhenStockNotInWallet() {
        // create data and mock
        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet(walletId);
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        when(walletStockRepository.findByWalletAndName(wallet, "example")).thenReturn(Optional.empty());

        // call
        int result = service.getStockQuantity(walletId, "example");

        // check result
        assertThat(result).isEqualTo(0);
    }
}