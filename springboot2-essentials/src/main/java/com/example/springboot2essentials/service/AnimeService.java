package com.example.springboot2essentials.service;

import com.example.springboot2essentials.domain.Anime;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimeService {
//  private final AnimeRepository animeRepository;

  public List<Anime> listAll() {
    return List.of(new Anime(1L, "Shingeki no Kyoujin"), new Anime(2L, "Gintama"));
  }
}


