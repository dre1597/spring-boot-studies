package com.example.demolombok.model;

import jakarta.persistence.*;

@Entity(name = "costumer")
public class CostumerModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 100, nullable = false)
  private String name;

  @Column(length = 150, nullable = false)
  private String address;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public String toString() {
    return "ClientModel{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", address='" + address + '\'' +
        '}';
  }
}
