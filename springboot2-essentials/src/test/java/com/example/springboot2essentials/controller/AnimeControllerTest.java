package com.example.springboot2essentials.controller;

import com.example.springboot2essentials.domain.Anime;
import com.example.springboot2essentials.service.AnimeService;
import com.example.springboot2essentials.util.AnimeCreator;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

}
