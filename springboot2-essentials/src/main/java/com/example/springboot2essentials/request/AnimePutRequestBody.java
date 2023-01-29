package com.example.springboot2essentials.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AnimePutRequestBody {

  @NotNull(message = "The anime id connot be null")
  @Min(1)
  private Long id;

  @NotBlank(message = "The anime name connot be empty")
  private String name;
}
