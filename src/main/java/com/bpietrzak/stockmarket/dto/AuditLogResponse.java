package com.bpietrzak.stockmarket.dto;

import java.util.List;

public record AuditLogResponse(List<LogDto> log) {}
