package com.example.demolombok.repository;

import com.example.demolombok.model.Solicitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitationRepository extends JpaRepository<Solicitation, String> {
}
