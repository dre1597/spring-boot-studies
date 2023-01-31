package com.example.springboot2essentials.util;

import com.example.springboot2essentials.request.AnimePutRequestBody;

public class AnimePutRequestBodyCreator {
  public static AnimePutRequestBody createAnimePutRequestBody() {
    return AnimePutRequestBody.builder()
        .id(AnimeCreator.createValidUpdatedAnime().getId())
        .name(AnimeCreator.createValidUpdatedAnime().getName())
        .build();
  }
}
