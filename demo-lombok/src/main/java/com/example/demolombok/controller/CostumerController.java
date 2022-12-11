package com.example.demolombok.controller;

import com.example.demolombok.model.Costumer;
import com.example.demolombok.repository.CostumerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/costumer")
public class CostumerController {

  private final CostumerRepository repository;

  public CostumerController(CostumerRepository repository) {
    this.repository = repository;
  }


  @GetMapping
  public ResponseEntity<List<Costumer>> index() {
    return ResponseEntity.ok(repository.findAll());
  }

  @PostMapping()
  public ResponseEntity<Costumer> store(@RequestBody Costumer costumer) {
    return ResponseEntity.ok(repository.save(costumer));
  }
}
