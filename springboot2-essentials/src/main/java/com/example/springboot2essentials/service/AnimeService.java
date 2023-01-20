package com.example.springboot2essentials.service;

import com.example.springboot2essentials.domain.Anime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AnimeService {
  private final List<Anime> animes = List.of(new Anime(1L, "Shingeki no Kyoujin"), new Anime(2L, "Gintama"));

//  private final AnimeRepository animeRepository;

  public List<Anime> find() {
    return animes;
  }

  public Anime findById(long id) {
    return animes
        .stream()
        .filter(anime -> anime.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Anime not found"));
  }
}


