package com.bpietrzak.stockmarket.service;

import com.bpietrzak.stockmarket.model.AuditLog;
import com.bpietrzak.stockmarket.model.enums.TransactionType;
import com.bpietrzak.stockmarket.repository.AuditLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditLogServiceTest {

    @Mock private AuditLogRepository auditLogRepository;

    @InjectMocks private AuditLogService service;

    @Test
    void getLogShouldReturnAllLogsOrderedByDate() {
        // create data and mock
        UUID walletId = UUID.randomUUID();
        AuditLog log = new AuditLog(TransactionType.BUY, walletId, "example");
        when(auditLogRepository.findAllByOrderByCreatedAtAsc()).thenReturn(List.of(log));

        // call
        var result = service.getLog();

        // check result
        assertThat(result.log()).hasSize(1);
        assertThat(result.log().getFirst().type()).isEqualTo(TransactionType.BUY);
        assertThat(result.log().getFirst().walletId()).isEqualTo(walletId);
        assertThat(result.log().getFirst().stockName()).isEqualTo("example");
    }
}
