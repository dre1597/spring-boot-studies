package com.example.demolombok.controller;

import com.example.demolombok.model.Solicitation;
import com.example.demolombok.repository.SolicitationRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/solicitation")
@AllArgsConstructor
public class SolicitationController {
  private final SolicitationRepository repository;

  @GetMapping
  public ResponseEntity<List<Solicitation>> index() {
    return ResponseEntity.ok(repository.findAll());
  }

  @PostMapping()
  public ResponseEntity<Solicitation> store(@RequestBody Solicitation solicitation) {
    if (solicitation.getId() == null || solicitation.getId().isEmpty()) {
      solicitation.setId(UUID.randomUUID().toString());
    }

    return ResponseEntity.ok(repository.save(solicitation));
  }
}
