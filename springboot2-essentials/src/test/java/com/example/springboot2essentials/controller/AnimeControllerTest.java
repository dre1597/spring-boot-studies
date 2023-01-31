package com.example.springboot2essentials.controller;

import com.example.springboot2essentials.domain.Anime;
import com.example.springboot2essentials.request.AnimePostRequestBody;
import com.example.springboot2essentials.request.AnimePutRequestBody;
import com.example.springboot2essentials.service.AnimeService;
import com.example.springboot2essentials.util.AnimeCreator;
import com.example.springboot2essentials.util.AnimePostRequestBodyCreator;
import com.example.springboot2essentials.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {
  @InjectMocks
  private AnimeController animeController;
  @Mock
  private AnimeService animeServiceMock;

  @BeforeEach
  void setup() {
    PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
    BDDMockito.when(animeServiceMock.find(ArgumentMatchers.any()))
        .thenReturn(animePage);

    BDDMockito.when(animeServiceMock.findAllNonPageable())
        .thenReturn(List.of(AnimeCreator.createValidAnime()));

    BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
        .thenReturn(AnimeCreator.createValidAnime());

    BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
        .thenReturn(List.of(AnimeCreator.createValidAnime()));

    BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
        .thenReturn(AnimeCreator.createValidAnime());

    BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class));

    BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());
  }

  @Test
  void shouldBePossibleToListAnimesWithPagination() {
    String expectedName = AnimeCreator.createValidAnime().getName();

    Page<Anime> animePage = animeController.find(null).getBody();

    Assertions.assertThat(animePage).isNotNull();
    Assertions.assertThat(animePage.toList())
        .isNotEmpty()
        .hasSize(1);
    Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
  }

  @Test
  void shouldBePossibleToListAnimesWithoutPagination() {
    String expectedName = AnimeCreator.createValidAnime().getName();

    List<Anime> animes = animeController.findAll().getBody();

    Assertions.assertThat(animes).
        isNotNull()
        .isNotEmpty()
        .hasSize(1);
    Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
  }

  @Test
  void shouldBePossibleToFindAnAnimeById() {
    Long expectedId = AnimeCreator.createValidAnime().getId();

    Anime anime = animeController.findById(0L).getBody();

    Assertions.assertThat(anime).
        isNotNull();

    Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
  }

  @Test
  void shouldBePossibleToFindAnAnimeByName() {
    String expectedName = AnimeCreator.createValidAnime().getName();

    List<Anime> animes = animeController.findByName("anime").getBody();

    Assertions.assertThat(animes).
        isNotNull()
        .isNotEmpty()
        .hasSize(1);
    Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
  }

  @Test
  void shouldReturnAnEmptyListWhenNotFindedAnAnimeByName() {
    BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
        .thenReturn(Collections.emptyList());

    List<Anime> animes = animeController.findByName("anime").getBody();

    Assertions.assertThat(animes)
        .isNotNull()
        .isEmpty();
  }

  @Test
  void shouldBePossibleToAddAnAnime() {
    Anime anime = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());

    Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());

  }

  @Test
  void shouldBePossibleToUpdateAnAnime() {

    Assertions.assertThatCode(() -> animeController.update(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
        .doesNotThrowAnyException();

    ResponseEntity<Void> entity = animeController.update(AnimePutRequestBodyCreator.createAnimePutRequestBody());

    Assertions.assertThat(entity).isNotNull();

    Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  void shouldBePossibleToDeleteAnAnime() {

    Assertions.assertThatCode(() -> animeController.delete(1))
        .doesNotThrowAnyException();

    ResponseEntity<Void> entity = animeController.delete(1);

    Assertions.assertThat(entity).isNotNull();

    Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }
}
