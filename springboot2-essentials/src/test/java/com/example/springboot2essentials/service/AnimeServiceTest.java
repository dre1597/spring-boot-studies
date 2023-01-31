package com.example.springboot2essentials.service;

import com.example.springboot2essentials.domain.Anime;
import com.example.springboot2essentials.exception.BadRequestException;
import com.example.springboot2essentials.repository.AnimeRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {
  @InjectMocks
  private AnimeService animeService;

  @Mock
  private AnimeRepository animeRepositoryMock;

  @BeforeEach
  void setUp() {
    PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
    BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
        .thenReturn(animePage);

    BDDMockito.when(animeRepositoryMock.findAll())
        .thenReturn(List.of(AnimeCreator.createValidAnime()));

    BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

    BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
        .thenReturn(List.of(AnimeCreator.createValidAnime()));

    BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
        .thenReturn(AnimeCreator.createValidAnime());

    BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
  }

  @Test
  void shouldBePossibleToListAnimesWithPagination() {
    String expectedName = AnimeCreator.createValidAnime().getName();

    Page<Anime> animePage = animeService.find(PageRequest.of(1, 1));

    Assertions.assertThat(animePage).isNotNull();

    Assertions.assertThat(animePage.toList())
        .isNotEmpty()
        .hasSize(1);

    Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
  }

  @Test
  void shouldBePossibleToListAnimesWithoutPagination() {
    String expectedName = AnimeCreator.createValidAnime().getName();

    List<Anime> animes = animeService.findAllNonPageable();

    Assertions.assertThat(animes)
        .isNotNull()
        .isNotEmpty()
        .hasSize(1);

    Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
  }

  @Test
  void shouldBePossibleToFindAnAnimeById() {
    Long expectedId = AnimeCreator.createValidAnime().getId();

    Anime anime = animeService.findByIdOrThrowBadRequestException(1);

    Assertions.assertThat(anime).isNotNull();

    Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
  }

  @Test
  void shouldThrownAnBadRequestExceptionIfNotFindedAnAnimeById() {
    BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.empty());

    Assertions.assertThatExceptionOfType(BadRequestException.class)
        .isThrownBy(() -> animeService.findByIdOrThrowBadRequestException(1));
  }

  @Test
  void shouldBePossibleToFindAnAnimeByName() {
    String expectedName = AnimeCreator.createValidAnime().getName();
    List<Anime> animes = animeService.findByName("anime");

    Assertions.assertThat(animes)
        .isNotNull()
        .isNotEmpty()
        .hasSize(1);

    Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
  }

  @Test
  void shouldReturnAnEmptyListWhenNotFindedAnAnimeByName() {
    BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
        .thenReturn(Collections.emptyList());

    List<Anime> animes = animeService.findByName("anime");

    Assertions.assertThat(animes)
        .isNotNull()
        .isEmpty();
  }

  @Test
  void shouldBePossibleToAddAnAnime() {
    Anime anime = animeService.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());

    Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
  }

  @Test
  void shouldBePossibleToUpdateAnAnime() {
    Assertions.assertThatCode(() -> animeService.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
        .doesNotThrowAnyException();
  }

  @Test
  void shouldBePossibleToDeleteAnAnime() {
    Assertions.assertThatCode(() -> animeService.delete(1))
        .doesNotThrowAnyException();
  }
}
