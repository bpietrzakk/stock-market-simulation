package com.bpietrzak.stockmarket.controller;

import com.bpietrzak.stockmarket.dto.AuditLogResponse;
import com.bpietrzak.stockmarket.service.AuditLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class AuditLogController {
    private final AuditLogService auditLogService;

    // CONSTRUCTOR
    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    // ENDPOINTS
    // GET: returns entire audit log in order of occurrence
    @GetMapping
    public ResponseEntity<AuditLogResponse> getLogs() {
        AuditLogResponse log = auditLogService.getLog();

        return ResponseEntity.ok(log);
    }
}
