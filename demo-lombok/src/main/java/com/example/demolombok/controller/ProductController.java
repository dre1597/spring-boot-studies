package com.example.demolombok.controller;

import com.example.demolombok.model.Product;
import com.example.demolombok.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {
  private final ProductRepository repository;
  
  @GetMapping
  public ResponseEntity<List<Product>> index() {
    return ResponseEntity.ok(repository.findAll());
  }

  @PostMapping()
  public ResponseEntity<Product> store(@RequestBody Product product) {
    return ResponseEntity.ok(repository.save(product));
  }
}
