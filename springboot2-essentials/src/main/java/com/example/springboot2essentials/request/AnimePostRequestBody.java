package com.example.springboot2essentials.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AnimePostRequestBody {
  @NotBlank(message = "The anime name connot be empty")
  private String name;
}
