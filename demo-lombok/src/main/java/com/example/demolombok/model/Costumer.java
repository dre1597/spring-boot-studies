package com.example.demolombok.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity(name = "costumer")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Costumer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 100, nullable = false)
  private String name;

  @Column(length = 150, nullable = false)
  private String address;

  @ManyToOne(cascade = CascadeType.ALL)
  private Solicitation order;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Costumer costumer = (Costumer) o;
    return id != null && Objects.equals(id, costumer.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
