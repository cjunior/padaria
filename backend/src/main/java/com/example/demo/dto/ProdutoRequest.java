package com.example.demo.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProdutoRequest(
    @NotBlank @Size(max = 150)
    String nome,
    @Size(max = 255)
    String descricao,
    @NotNull
    BigDecimal preco,
    Integer quantidadeEstoque,
    Boolean ativo,
    @NotNull
    Long categoriaId
) {}
