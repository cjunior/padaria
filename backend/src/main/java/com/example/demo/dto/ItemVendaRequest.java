package com.example.demo.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ItemVendaRequest(
    @NotNull
    Integer quantidade,
    @NotNull
    BigDecimal precoUnitario,
    @NotNull
    BigDecimal subtotal,
    @NotNull
    Long vendaId,
    @NotNull
    Long produtoId
) {}
