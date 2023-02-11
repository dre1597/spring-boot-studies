package com.example.springboot2essentials.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimePostRequestBody {
  @NotBlank(message = "The anime name connot be empty")
  @Schema(description = "This is the Anime's name", example = "One Piece")
  private String name;
}
