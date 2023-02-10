package com.example.springboot2essentials.service;

import com.example.springboot2essentials.repository.CustomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {
  private final CustomUserRepository customUserRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    return Optional.ofNullable(customUserRepository.findByUsername(username))
        .orElseThrow(() -> new UsernameNotFoundException("Custom User not found"));
  }
}
