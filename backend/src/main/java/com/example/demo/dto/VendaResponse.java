package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VendaResponse(
    Long id,
    BigDecimal valorTotal,
    String status,
    String formaPagamento,
    LocalDateTime criadoEm,
    Long clienteId
) {}
