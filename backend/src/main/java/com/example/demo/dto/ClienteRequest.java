package com.example.demo.dto;

import jakarta.validation.constraints.*;

public record ClienteRequest(
    @NotBlank @Size(max = 150)
    String nome,
    @Size(max = 20)
    String telefone,
    @Size(max = 150)
    String email
) {}
