package com.example.springboot2essentials.integration;

import com.example.springboot2essentials.domain.Anime;
import com.example.springboot2essentials.domain.CustomUser;
import com.example.springboot2essentials.repository.AnimeRepository;
import com.example.springboot2essentials.repository.CustomUserRepository;
import com.example.springboot2essentials.request.AnimePostRequestBody;
import com.example.springboot2essentials.util.AnimeCreator;
import com.example.springboot2essentials.util.AnimePostRequestBodyCreator;
import com.example.springboot2essentials.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
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
  private static final CustomUser USER = CustomUser.builder()
      .name("user")
      .password("{bcrypt}$2a$10$7YN36wvitJdrIV4S7wysfu.MoBoi9Y.9QdKk80x5Da5FOD9Zsz34G")
      .username("user")
      .authorities("ROLE_USER")
      .build();

  private static final CustomUser ADMIN = CustomUser.builder()
      .name("admin")
      .password("{bcrypt}$2a$10$7YN36wvitJdrIV4S7wysfu.MoBoi9Y.9QdKk80x5Da5FOD9Zsz34G")
      .username("admin")
      .authorities("ROLE_USER,ROLE_ADMIN")
      .build();

  @Autowired
  @Qualifier(value = "testRestTemplateRoleUser")
  private TestRestTemplate testRestTemplateRoleUser;

  @Autowired
  @Qualifier(value = "testRestTemplateRoleAdmin")
  private TestRestTemplate testRestTemplateRoleAdmin;

  @Autowired
  private AnimeRepository animeRepository;

  @Autowired
  private CustomUserRepository customUserRepository;

  @Test
  void shouldBePossibleToListAnimesWithPagination() {
    Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
    customUserRepository.save(USER);

    String expectedName = savedAnime.getName();

    PageableResponse<Anime> animePage = testRestTemplateRoleUser
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
    customUserRepository.save(USER);

    String expectedName = savedAnime.getName();

    List<Anime> animes = testRestTemplateRoleUser
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
    customUserRepository.save(USER);

    Long expectedId = savedAnime.getId();

    Anime anime = testRestTemplateRoleUser.getForObject("/animes/{id}", Anime.class, expectedId);

    Assertions.assertThat(anime).
        isNotNull();
    Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
  }

  @Test
  void shouldBePossibleToFindAnAnimeByName() {
    Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
    customUserRepository.save(USER);

    String expectedName = savedAnime.getName();

    String url = String.format("/animes/filterBy?name=%s", expectedName);

    List<Anime> animes = testRestTemplateRoleUser.exchange(
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
    customUserRepository.save(USER);

    String expectedName = "animeThatIsntInTheList";

    String url = String.format("/animes/filterBy?name=%s", expectedName);

    List<Anime> animes = testRestTemplateRoleUser.exchange(
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
  void shouldBePossibleToAddAnAnimeWithAnAdmin() {
    customUserRepository.save(ADMIN);

    AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
    System.out.println(animePostRequestBody);
    ResponseEntity<Anime> animeResponseEntity = testRestTemplateRoleAdmin.postForEntity("/animes/admin", animePostRequestBody, Anime.class);

    Assertions.assertThat(animeResponseEntity).isNotNull();
    Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    Assertions.assertThat(animeResponseEntity.getBody()).isNotNull();
    Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull();
  }

  @Test
  void shouldBePossibleToAddAnAnimeWithAnUser() {
    customUserRepository.save(USER);

    AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
    System.out.println(animePostRequestBody);
    ResponseEntity<Anime> animeResponseEntity = testRestTemplateRoleUser.postForEntity("/animes/admin", animePostRequestBody, Anime.class);

    Assertions.assertThat(animeResponseEntity).isNotNull();
    Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  @Test
  void shouldBePossibleToUpdateAnAnime() {
    Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
    customUserRepository.save(ADMIN);

    savedAnime.setName("new name");

    ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleAdmin.exchange(
        "/animes/admin",
        HttpMethod.PUT,
        new HttpEntity<>(savedAnime),
        Void.class
    );

    Assertions.assertThat(animeResponseEntity).isNotNull();
    Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  void shouldNotBePossibleToUpdateAnAnimeWithAnUser() {
    Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
    customUserRepository.save(USER);

    savedAnime.setName("new name");

    ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleUser.exchange(
        "/animes/admin",
        HttpMethod.PUT,
        new HttpEntity<>(savedAnime),
        Void.class
    );

    Assertions.assertThat(animeResponseEntity).isNotNull();
    Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  @Test
  void shouldBePossibleToDeleteAnAnimeWithAnAdmin() {
    Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
    customUserRepository.save(ADMIN);

    ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleAdmin.exchange("/animes/admin/{id}",
        HttpMethod.DELETE, null, Void.class, savedAnime.getId());

    Assertions.assertThat(animeResponseEntity).isNotNull();
    Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  void shouldNotBePossibleToDeleteAnAnimeWithAnUser() {
    Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
    customUserRepository.save(USER);

    ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleUser.exchange("/animes/admin/{id}",
        HttpMethod.DELETE, null, Void.class, savedAnime.getId());

    Assertions.assertThat(animeResponseEntity).isNotNull();
    Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  @TestConfiguration
  @Lazy
  static class Config {
    @Bean(name = "testRestTemplateRoleUser")
    public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
      RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
          .rootUri("http://localhost:" + port)
          .basicAuthentication("user", "userpassword");

      return new TestRestTemplate(restTemplateBuilder);
    }

    @Bean(name = "testRestTemplateRoleAdmin")
    public TestRestTemplate testRestTemplateRoleUserAdminCreator(@Value("${local.server.port}") int port) {
      RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
          .rootUri("http://localhost:" + port)
          .basicAuthentication("admin", "userpassword");

      return new TestRestTemplate(restTemplateBuilder);
    }
  }
}
