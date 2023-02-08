package com.example.springboot2essentials.integration;

import com.example.springboot2essentials.domain.Anime;
import com.example.springboot2essentials.repository.AnimeRepository;
import com.example.springboot2essentials.request.AnimePostRequestBody;
import com.example.springboot2essentials.util.AnimeCreator;
import com.example.springboot2essentials.util.AnimePostRequestBodyCreator;
import com.example.springboot2essentials.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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

  @Test
  void shouldBePossibleToListAnimesWithoutPagination() {
    Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

    String expectedName = savedAnime.getName();

    List<Anime> animes = testRestTemplate
        .exchange(
            "/animes/all",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Anime>>() {
            }
        ).getBody();

    Assertions.assertThat(animes).
        isNotNull()
        .isNotEmpty()
        .hasSize(1);
    Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
  }

  @Test
  void shouldBePossibleToFindAnAnimeById() {
    Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

    Long expectedId = savedAnime.getId();

    Anime anime = testRestTemplate.getForObject("/animes/{id}", Anime.class, expectedId);

    Assertions.assertThat(anime).
        isNotNull();
    Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
  }

  @Test
  void shouldBePossibleToFindAnAnimeByName() {
    Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

    String expectedName = savedAnime.getName();

    String url = String.format("/animes/filterBy?name=%s", expectedName);

    List<Anime> animes = testRestTemplate.exchange(
        url,
        HttpMethod.GET, null,
        new ParameterizedTypeReference<List<Anime>>() {
        }
    ).getBody();


    Assertions.assertThat(animes).
        isNotNull()
        .isNotEmpty()
        .hasSize(1);
    Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
  }

  @Test
  void shouldReturnAnEmptyListWhenNotFindedAnAnimeByName() {
    String expectedName = "animeThatIsntInTheList";

    String url = String.format("/animes/filterBy?name=%s", expectedName);

    List<Anime> animes = testRestTemplate.exchange(
        url,
        HttpMethod.GET, null,
        new ParameterizedTypeReference<List<Anime>>() {
        }
    ).getBody();

    Assertions.assertThat(animes)
        .isNotNull()
        .isEmpty();
  }

  @Test
  void shouldBePossibleToAddAnAnime() {
    AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
    System.out.println(animePostRequestBody);
    ResponseEntity<Anime> animeResponseEntity = testRestTemplate.postForEntity("/animes", animePostRequestBody, Anime.class);

    System.out.println(animeResponseEntity);

    Assertions.assertThat(animeResponseEntity).isNotNull();
    Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    Assertions.assertThat(animeResponseEntity.getBody()).isNotNull();
    Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull();
  }

  @Test
  void shouldBePossibleToUpdateAnAnime() {
    Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

    savedAnime.setName("new name");

    ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange(
        "/animes",
        HttpMethod.PUT,
        new HttpEntity<>(savedAnime),
        Void.class
    );

    Assertions.assertThat(animeResponseEntity).isNotNull();
    Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  void shouldBePossibleToDeleteAnAnime() {
    Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

    ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange("/animes/{id}",
        HttpMethod.DELETE, null, Void.class, savedAnime.getId());

    Assertions.assertThat(animeResponseEntity).isNotNull();
    Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }
}
