package com.example.mbspringboot3.product;

import com.example.mbspringboot3.product.dto.ProductInputDto;
import com.example.mbspringboot3.product.dto.ProductOutputDto;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductController {
  @Autowired
  ProductRepository productRepository;

  @GetMapping("/products")
  public ResponseEntity<List<ProductOutputDto>> findAll() {
    List<ProductModel> products = productRepository.findAll();

    return new ResponseEntity<>(products.stream().map(p -> new ProductOutputDto(p.getId(), p.getName(), p.getValue())).toList(), HttpStatus.OK);
  }

  @PostMapping("/products")
  public ResponseEntity<ProductOutputDto> save(@RequestBody @Valid ProductInputDto productDto) {
    ProductModel productModel = new ProductModel();

    BeanUtils.copyProperties(productDto, productModel);

    ProductModel productSaved = productRepository.save(productModel);

    ProductOutputDto output = new ProductOutputDto(productSaved.getId(), productSaved.getName(), productSaved.getValue());

    return ResponseEntity.status(HttpStatus.CREATED).body(output);
  }

  @GetMapping("/products/{id}")
  public ResponseEntity<Object> findById(@PathVariable(value = "id") UUID id) {
    Optional<ProductModel> productOptional = productRepository.findById(id);

    if (productOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }

    ProductModel productModel = productOptional.get();
    ProductOutputDto output = new ProductOutputDto(productModel.getId(), productModel.getName(), productModel.getValue());

    return ResponseEntity.status(HttpStatus.OK).body(output);
  }

  @PutMapping("/products/{id}")
  public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductInputDto productDto) {
    Optional<ProductModel> productOptional = productRepository.findById(id);

    if (productOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }

    ProductModel productModel = productOptional.get();
    BeanUtils.copyProperties(productDto, productModel);
    productRepository.save(productModel);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/products/{id}")
  public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
    Optional<ProductModel> productOptional = productRepository.findById(id);

    if (productOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }

    productRepository.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
