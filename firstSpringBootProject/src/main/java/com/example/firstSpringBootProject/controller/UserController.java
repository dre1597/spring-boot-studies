package com.example.firstSpringBootProject.controller;

import com.example.firstSpringBootProject.entity.User;
import com.example.firstSpringBootProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping("/users")
  public List<User> index() {
    return userService.getAll();
  }

  @PostMapping("/users")
  public Long store(@RequestBody User user) {
    userService.saveOrUpdate(user);
    return user.getId();
  }

  @GetMapping("/users/{id}")
  public Optional<User> show(@PathVariable("id") Long id) {
    return userService.getOne(id);
  }

  @PutMapping("/users")
  public Long update(@RequestBody User user) {
    userService.saveOrUpdate(user);
    return user.getId();
  }

  @DeleteMapping("/users/{id}")
  public void destroy(@PathVariable("id") Long id) {
    userService.remove(id);
  }
}
