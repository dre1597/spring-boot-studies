package com.example.demo.controller;

import com.example.demo.entity.OrderProduct;
import com.example.demo.service.OrderProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/order-product")
@AllArgsConstructor
public class OrderProductController {
  private OrderProductService service;

  @PostMapping
  public ResponseEntity<OrderProduct> save(@RequestBody OrderProduct orderProduct) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(orderProduct));
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<OrderProduct> findById(@PathVariable Long id) {
    return ResponseEntity.ok().body(service.findById(id));
  }

  @GetMapping
  public ResponseEntity<List<OrderProduct>> findAll() {
    return ResponseEntity.ok().body(service.findAll());
  }
}
