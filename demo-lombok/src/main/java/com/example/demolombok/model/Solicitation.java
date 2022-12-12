package com.example.demolombok.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Entity(name = "solicitation")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Solicitation {
  @Id
  private String id;

  @ManyToOne(cascade = CascadeType.ALL)
  private Costumer costumer;

  @OneToMany(cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<Item> items;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Solicitation order = (Solicitation) o;
    return id != null && Objects.equals(id, order.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
