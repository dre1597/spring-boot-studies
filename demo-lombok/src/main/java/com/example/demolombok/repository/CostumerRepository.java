package com.example.demolombok.repository;

import com.example.demolombok.model.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostumerRepository extends JpaRepository<Costumer, Integer> {
}
