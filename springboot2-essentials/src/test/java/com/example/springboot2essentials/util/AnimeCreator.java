package com.example.springboot2essentials.util;

import com.example.springboot2essentials.domain.Anime;

public class AnimeCreator {

  public static Anime createAnimeToBeSaved(){
    return Anime.builder()
        .name("Any anime")
        .build();
  }

  public static Anime createValidAnime(){
    return Anime.builder()
        .name("Any anime")
        .id(1L)
        .build();
  }

  public static Anime createValidUpdatedAnime(){
    return Anime.builder()
        .name("Any anime 2")
        .id(1L)
        .build();
  }
}
