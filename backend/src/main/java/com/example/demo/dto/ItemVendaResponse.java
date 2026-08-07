package com.example.demo.dto;

import java.math.BigDecimal;

public record ItemVendaResponse(
    Long id,
    Integer quantidade,
    BigDecimal precoUnitario,
    BigDecimal subtotal,
    Long vendaId,
    Long produtoId
) {}
