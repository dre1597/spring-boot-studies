package com.example.springboot2essentials.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimePutRequestBody {

  @NotNull(message = "The anime id connot be null")
  @Min(1)
  private Long id;

  @NotBlank(message = "The anime name connot be empty")
  private String name;
}
