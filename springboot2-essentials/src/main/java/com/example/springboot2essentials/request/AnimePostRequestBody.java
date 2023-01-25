package com.example.springboot2essentials.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AnimePostRequestBody {
  @NotEmpty(message = "The anime name connot be empty")
  private String name;
}
