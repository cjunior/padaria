package com.example.demo.dto;

import java.time.LocalDateTime;

public record CategoriaResponse(
    Long id,
    String nome,
    String descricao,
    Boolean ativo,
    LocalDateTime criadoEm
) {}
