package com.example.springboot2essentials.util;

import com.example.springboot2essentials.request.AnimePostRequestBody;

public class AnimePostRequestBodyCreator {
  public static AnimePostRequestBody createAnimePostRequestBody() {
    return AnimePostRequestBody.builder()
        .name(AnimeCreator.createAnimeToBeSaved().getName())
        .build();
  }
}
