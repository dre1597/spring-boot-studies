package com.example.demolombok.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 100, nullable = false)
  private String description;

  @Column(nullable = false)
  private BigDecimal price;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "ProductModel{" +
        "id=" + id +
        ", description='" + description + '\'' +
        ", price=" + price +
        '}';
  }
}
