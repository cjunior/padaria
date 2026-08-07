package com.example.demo.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record VendaRequest(
    BigDecimal valorTotal,
    @Size(max = 20)
    String status,
    @Size(max = 30)
    String formaPagamento,
    Long clienteId
) {}
