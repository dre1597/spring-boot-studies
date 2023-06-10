package com.example.mbspringboot3.product;

import com.example.mbspringboot3.product.dto.ProductInputDto;
import com.example.mbspringboot3.product.dto.ProductOutputDto;
import com.example.mbspringboot3.product.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProductService {
  @Autowired
  ProductRepository productRepository;

  public List<ProductOutputDto> findAll() {
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

    return productsOutput;
  }

  public void insert(ProductInputDto productDto) {
    ProductModel productModel = new ProductModel();

    BeanUtils.copyProperties(productDto, productModel);

    productRepository.save(productModel);
  }

  public ProductOutputDto findById(UUID id) {
    Optional<ProductModel> productOptional = productRepository.findById(id);

    if (productOptional.isEmpty()) {
      throw new NotFoundException();
    }

    ProductModel productModel = productOptional.get();
    ProductOutputDto output = new ProductOutputDto(productModel.getId(), productModel.getName(), productModel.getValue());
    output.add(linkTo(methodOn(ProductController.class).findAll()).withRel("Products list"));

    return output;
  }

  public void update(UUID id, ProductInputDto productDto) {
    Optional<ProductModel> productOptional = productRepository.findById(id);

    if (productOptional.isEmpty()) {
      throw new NotFoundException();
    }

    ProductModel productModel = productOptional.get();
    BeanUtils.copyProperties(productDto, productModel);
    productRepository.save(productModel);
  }

  public void delete(UUID id) {
    Optional<ProductModel> productOptional = productRepository.findById(id);
    
    if (productOptional.isEmpty()) {
      throw new NotFoundException();
    }

    productRepository.deleteById(id);
  }
}
