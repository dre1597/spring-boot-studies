package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ProductService {

  private ProductRepository repository;

  public Product save(Product product) {
    return repository.save(product);
  }

  public Product findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  public List<Product> findAll() {
    return repository.findAll();
  }
}
