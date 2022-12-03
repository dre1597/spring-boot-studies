package com.example.firstSpringBootProject.service;

import com.example.firstSpringBootProject.entity.User;
import com.example.firstSpringBootProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public void saveOrUpdate(User user) {
    this.userRepository.save(user);
  }

  public Optional<User> getOne(Long id) {
    return this.userRepository.findById(id);
  }

  public void remove(Long id) {
    this.userRepository.deleteById(id);
  }
}
