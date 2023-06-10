package com.example.mbspringboot3.product;

import com.example.mbspringboot3.product.dto.ProductInputDto;
import com.example.mbspringboot3.product.dto.ProductOutputDto;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProductController {
  private static final String NOT_FOUND_MESSAGE = "Product not found";
  @Autowired
  ProductRepository productRepository;

  @GetMapping("/products")
  public ResponseEntity<List<ProductOutputDto>> findAll() {
    List<ProductModel> products = productRepository.findAll();

    List<ProductOutputDto> productsOutput = new ArrayList<>();

    if (!products.isEmpty()) {
      for (ProductModel product : products) {
        UUID id = product.getId();
        ProductOutputDto output = new ProductOutputDto(id, product.getName(), product.getValue());
        output.add(linkTo(methodOn(ProductController.class).findById(id)).withSelfRel());
        productsOutput.add(output);
      }
    }

    return new ResponseEntity<>(productsOutput, HttpStatus.OK);
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
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_MESSAGE);
    }

    ProductModel productModel = productOptional.get();
    ProductOutputDto output = new ProductOutputDto(productModel.getId(), productModel.getName(), productModel.getValue());
    output.add(linkTo(methodOn(ProductController.class).findAll()).withRel("Products list"));

    return ResponseEntity.status(HttpStatus.OK).body(output);
  }

  @PutMapping("/products/{id}")
  public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductInputDto productDto) {
    Optional<ProductModel> productOptional = productRepository.findById(id);

    if (productOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_MESSAGE);
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
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_MESSAGE);
    }

    productRepository.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
