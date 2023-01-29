package com.example.springboot2essentials.repository;

import com.example.springboot2essentials.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@Log4j2
class AnimeRepositoryTest {
  @Autowired
  private AnimeRepository animeRepository;

  @Test
  void shouldBePossibleToSaveAnAnime() {
    Anime animeToBeSaved = createAnime();
    Anime savedAnime = this.animeRepository.save(animeToBeSaved);

    Assertions.assertThat(savedAnime).isNotNull();
    Assertions.assertThat(savedAnime.getId()).isNotNull();
    Assertions.assertThat(savedAnime.getName()).isEqualTo(animeToBeSaved.getName());
  }

  @Test
  void shouldNotBePossibleToSavenAnAnimeWithoutAnName() {
    Anime animeToBeSaved = createAnime();

    animeToBeSaved.setName("");

    Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
        .isThrownBy(() -> this.animeRepository.save(animeToBeSaved))
        .withMessageContaining("The anime name connot be empty");
  }

  @Test
  void shouldNotBePossibleToSavenAnAnimeWithNullAsNameValue() {
    Anime animeToBeSaved = createAnime();

    animeToBeSaved.setName(null);

    Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
        .isThrownBy(() -> this.animeRepository.save(animeToBeSaved))
        .withMessageContaining("The anime name connot be empty");
  }

  @Test
  void shouldBePossibleToUpdateAnAnime() {
    Anime animeToBeSaved = createAnime();

    Anime savedAnime = this.animeRepository.save(animeToBeSaved);

    savedAnime.setName("Teste Anime 2");

    Anime animeUpdated = this.animeRepository.save(savedAnime);

    Assertions.assertThat(animeUpdated).isNotNull();

    Assertions.assertThat(animeUpdated.getId()).isNotNull();

    Assertions.assertThat(animeUpdated.getName()).isEqualTo(savedAnime.getName());
  }

  @Test
  void shouldBePossibleToDeleteAnAnime() {
    Anime animeToBeSaved = createAnime();

    Anime savedAnime = this.animeRepository.save(animeToBeSaved);

    this.animeRepository.delete(savedAnime);

    Optional<Anime> animeOptional = this.animeRepository.findById(savedAnime.getId());

    Assertions.assertThat(animeOptional).isEmpty();

  }

  @Test
  void shouldBePossibleToFindAnAnimeByItName() {
    Anime animeToBeSaved = createAnime();

    Anime animeSaved = this.animeRepository.save(animeToBeSaved);

    String name = animeSaved.getName();

    List<Anime> animes = this.animeRepository.findByName(name);

    Assertions.assertThat(animes)
        .isNotEmpty()
        .contains(animeSaved);

  }

  @Test
  void shouldReturnAnEmptyListIfThereIsNoAnimeWithThePassedName() {
    List<Anime> animes = this.animeRepository.findByName("Anime that isn't in the database");

    Assertions.assertThat(animes).isEmpty();
  }

  @Test
  void shouldNotBePossibleToFindAnAnimeByNameWithoutAName() {
    Anime anime = new Anime();
//        Assertions.assertThatThrownBy(() -> this.animeRepository.save(anime))
//                .isInstanceOf(ConstraintViolationException.class);

    Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
        .isThrownBy(() -> this.animeRepository.save(anime))
        .withMessageContaining("The anime name connot be empty");
  }

  private Anime createAnime() {
    return Anime.builder().name("Test anime").build();
  }
}
