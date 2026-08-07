package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoResponse(
    Long id,
    String nome,
    String descricao,
    BigDecimal preco,
    Integer quantidadeEstoque,
    Boolean ativo,
    LocalDateTime criadoEm,
    Long categoriaId
) {}
