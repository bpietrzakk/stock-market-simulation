package com.bpietrzak.stockmarket.repository;

import com.bpietrzak.stockmarket.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findAllByOrderByCreatedAtAsc();
}
