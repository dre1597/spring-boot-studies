package com.example.demolombok.controller;

import com.example.demolombok.model.Costumer;
import com.example.demolombok.repository.CostumerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/costumer")
@AllArgsConstructor
public class CostumerController {

  private final CostumerRepository repository;
  
  @GetMapping
  public ResponseEntity<List<Costumer>> index() {
    return ResponseEntity.ok(repository.findAll());
  }

  @PostMapping()
  public ResponseEntity<Costumer> store(@RequestBody Costumer costumer) {
    return ResponseEntity.ok(repository.save(costumer));
  }
}
