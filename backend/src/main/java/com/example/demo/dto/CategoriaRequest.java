package com.example.demo.dto;

import jakarta.validation.constraints.*;

public record CategoriaRequest(
    @NotBlank @Size(max = 100)
    String nome,
    @Size(max = 255)
    String descricao,
    Boolean ativo
) {}
