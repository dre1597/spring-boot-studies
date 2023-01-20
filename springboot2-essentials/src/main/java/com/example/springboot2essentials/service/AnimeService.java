package com.example.springboot2essentials.service;

import com.example.springboot2essentials.domain.Anime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AnimeService {
  private static final List<Anime> animes;

  static {
    animes = new ArrayList<>(List.of(new Anime(1L, "Shingeki no Kyoujin"), new Anime(2L, "Gintama")));
  }

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

  public void save(Anime anime) {
    anime.setId(ThreadLocalRandom.current().nextLong(3, 100000));
    animes.add(anime);
  }

  public void delete(long id) {
    animes.remove(findById(id));
  }

  public void replace(Anime anime) {
    delete(anime.getId());
    animes.add(anime);
  }
}


