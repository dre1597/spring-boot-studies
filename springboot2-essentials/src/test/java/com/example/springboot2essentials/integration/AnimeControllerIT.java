package com.example.springboot2essentials.integration;

import com.example.springboot2essentials.domain.Anime;
import com.example.springboot2essentials.repository.AnimeRepository;
import com.example.springboot2essentials.util.AnimeCreator;
import com.example.springboot2essentials.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class AnimeControllerIT {
  @Autowired
  private TestRestTemplate testRestTemplate;

  @Autowired
  private AnimeRepository animeRepository;

  @Test
  void shouldBePossibleToListAnimesWithPagination() {
    Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

    String expectedName = savedAnime.getName();

    PageableResponse<Anime> animePage = testRestTemplate
        .exchange(
            "/animes",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<PageableResponse<Anime>>() {
            }
        ).getBody();

    Assertions.assertThat(animePage).isNotNull();
    Assertions.assertThat(animePage.toList())
        .isNotEmpty()
        .hasSize(1);
    Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
  }
}