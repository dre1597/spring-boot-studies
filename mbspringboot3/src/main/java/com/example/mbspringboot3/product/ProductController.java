package com.example.mbspringboot3.product;

import com.example.mbspringboot3.product.dto.ProductInputDto;
import com.example.mbspringboot3.product.dto.ProductOutputDto;
import com.example.mbspringboot3.product.exception.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {
  private static final String NOT_FOUND_MESSAGE = "Product not found";
  @Autowired
  ProductRepository productRepository;

  @Autowired
  ProductService productService;

  @GetMapping("/products")
  public ResponseEntity<List<ProductOutputDto>> findAll() {
    List<ProductOutputDto> products = productService.findAll();
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @PostMapping("/products")
  public ResponseEntity<ProductOutputDto> save(@RequestBody @Valid ProductInputDto productDto) {
    productService.insert(productDto);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/products/{id}")
  public ResponseEntity<Object> findById(@PathVariable(value = "id") UUID id) {
    try {
      ProductOutputDto output = productService.findById(id);
      return ResponseEntity.status(HttpStatus.OK).body(output);
    } catch (NotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_MESSAGE);
    }
  }

  @PutMapping("/products/{id}")
  public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductInputDto productDto) {
    try {
      productService.update(id, productDto);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (NotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_MESSAGE);
    }
  }

  @DeleteMapping("/products/{id}")
  public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
    try {
      productService.delete(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (NotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_MESSAGE);
    }
  }
}
