package com.example.mbspringboot3.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductInputDto(@NotBlank String name, @NotNull BigDecimal value) {
}
