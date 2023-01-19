package com.example.demo.service;

import com.example.demo.entity.OrderProduct;
import com.example.demo.entity.Product;
import com.example.demo.repository.OrderProductRepository;
import com.example.demo.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class OrderProductService {
  private OrderProductRepository repository;

  private ProductRepository productRepository;

  public OrderProduct save(OrderProduct orderProduct) {
    Set<Product> products = new HashSet<>();

    orderProduct.setOrderDate(LocalDateTime.now());
    orderProduct.getProducts().forEach(product -> productRepository.findById(product.getId()).ifPresent(products::add));
    orderProduct.setProducts(products);

    return repository.save(orderProduct);
  }

  public OrderProduct findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  public List<OrderProduct> findAll() {
    return repository.findAll();
  }
}
