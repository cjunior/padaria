package com.example.demo.dto;

import java.time.LocalDateTime;

public record ClienteResponse(
    Long id,
    String nome,
    String telefone,
    String email,
    LocalDateTime criadoEm
) {}
