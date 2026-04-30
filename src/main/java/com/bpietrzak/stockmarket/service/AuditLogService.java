package com.bpietrzak.stockmarket.service;

import com.bpietrzak.stockmarket.dto.AuditLogResponse;
import com.bpietrzak.stockmarket.dto.LogDto;
import com.bpietrzak.stockmarket.repository.AuditLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuditLogService {
    private final AuditLogRepository auditLogRepository;

    // CONSTRUCTOR
    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    // methods
    @Transactional(readOnly = true)
    public AuditLogResponse getLog() {
        List<LogDto> logs = auditLogRepository.findAllByOrderByCreatedAtAsc().stream()
                .map(al -> new LogDto(al.getType(), al.getWalletId(), al.getStockName()))
                .toList();
        return new AuditLogResponse(logs);
    }
}
