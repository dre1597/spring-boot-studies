package com.example.mbspringboot3.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductOutputDto(@NotBlank UUID id, @NotBlank String name, @NotNull BigDecimal value) {
}
