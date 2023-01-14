package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.exception.InvalidFieldsException;
import com.example.demo.exception.InvalidPriceException;
import com.example.demo.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductService {

  private ProductRepository repository;

  public Product save(Product product) {
    if (product.getName() == null || product.getPrice() == null) {
      throw new InvalidFieldsException();
    }

    if (product.getPrice() <= 0) {
      throw new InvalidPriceException();
    }

    return repository.save(product);
  }

  public Product findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  public List<Product> findAll() {
    return repository.findAll();
  }
}
