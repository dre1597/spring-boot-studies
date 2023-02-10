package com.example.springboot2essentials.repository;

import com.example.springboot2essentials.domain.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {
  CustomUser findByUsername(String username);
}
