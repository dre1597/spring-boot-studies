package com.example.demolombok.controller;

import com.example.demolombok.model.ProductModel;
import com.example.demolombok.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
  private final ProductRepository repository;

  public ProductController(ProductRepository repository) {
    this.repository = repository;
  }


  @GetMapping
  public ResponseEntity<List<ProductModel>> index() {
    return ResponseEntity.ok(repository.findAll());
  }

  @PostMapping()
  public ResponseEntity<ProductModel> store(@RequestBody ProductModel product) {
    return ResponseEntity.ok(repository.save(product));
  }
}
