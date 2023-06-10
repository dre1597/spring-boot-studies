package com.example.mbspringboot3.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class ProductOutputDto extends RepresentationModel<ProductOutputDto> implements Serializable {
  private UUID id;

  private String name;

  private BigDecimal value;

  public ProductOutputDto(@NotBlank UUID id, @NotBlank String name, @NotNull BigDecimal value) {
    this.id = id;
    this.name = name;
    this.value = value;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    ProductOutputDto that = (ProductOutputDto) o;
    return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getValue(), that.getValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getId(), getName(), getValue());
  }
}
