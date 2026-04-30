package com.bpietrzak.stockmarket.dto;

import java.util.List;

public class AuditLogResponse {
    private final List<LogDto> log;

    // CONSTRUCTOR
    public AuditLogResponse(List<LogDto> log) {
        this.log = log;
    }

    // getters
    public List<LogDto> getLog() { return log; }
}
