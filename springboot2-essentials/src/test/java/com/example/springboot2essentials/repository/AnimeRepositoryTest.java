package com.example.springboot2essentials.repository;

import com.example.springboot2essentials.domain.Anime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AnimeRepositoryTest {
  @Autowired
  private AnimeRepository animeRepository;

  @Test
  void shouldPersistsAnime() {
    Anime animeToBeSaved = createAnime();
    Anime savedAnime = this.animeRepository.save(animeToBeSaved);

    Assertions.assertNotNull(savedAnime);
    Assertions.assertNotNull(savedAnime.getId());
    Assertions.assertEquals(savedAnime.getName(), animeToBeSaved.getName());
  }

  private Anime createAnime() {
    return Anime.builder().name("Test anime").build();
  }
}
