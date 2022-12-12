package com.example.demolombok.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "item")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(cascade = CascadeType.ALL)
  private Product product;

  private BigDecimal price;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Item item = (Item) o;
    return id != null && Objects.equals(id, item.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
