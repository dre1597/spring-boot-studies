package com.example.demolombok.repository;

import com.example.demolombok.model.CostumerModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostumerRepository extends JpaRepository<CostumerModel, Integer> {
}
