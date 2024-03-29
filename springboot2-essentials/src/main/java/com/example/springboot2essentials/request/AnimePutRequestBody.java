package com.example.springboot2essentials.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
  @Schema(description = "This is the Anime's id on the database", example = "1")
  private Long id;

  @NotBlank(message = "The anime name connot be empty")
  @Schema(description = "This is the Anime's name", example = "One Piece")
  private String name;
}
