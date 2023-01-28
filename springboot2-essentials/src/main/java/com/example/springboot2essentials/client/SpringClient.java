package com.example.springboot2essentials.client;

import com.example.springboot2essentials.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
  public static void main(String[] args) {
    String baseUrl = "http://localhost:8080/animes/";

    ResponseEntity<Anime> entity = new RestTemplate().getForEntity(baseUrl + "{id}", Anime.class,2);
    log.info(entity);

    Anime object = new RestTemplate().getForObject(baseUrl + "{id}", Anime.class,2);
    log.info(object);

    Anime[] animes = new RestTemplate().getForObject(baseUrl + "all", Anime[].class);

    log.info(Arrays.toString(animes));

    ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange(baseUrl + "all",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<>() {});
    log.info(exchange.getBody());

    //        Anime kingdom = Anime.builder().name("kingdom").build();
    //        Anime kingdomSaved = new RestTemplate().postForObject(baseUrl, kingdom, Anime.class);
    //        log.info("saved anime {}", kingdomSaved);

    Anime samuraiChamploo = Anime.builder().name("Samurai Champloo").build();
    ResponseEntity<Anime> samuraiChamplooSaved = new RestTemplate().exchange(baseUrl,
        HttpMethod.POST,
        new HttpEntity<>(samuraiChamploo, createJsonHeader()),
        Anime.class);

    log.info("saved anime {}", samuraiChamplooSaved);

    Anime animeToBeUpdated = samuraiChamplooSaved.getBody();

    if (animeToBeUpdated != null) {
      animeToBeUpdated.setName("Samurai Champloo 2");
    }

    ResponseEntity<Void> samuraiChamplooUpdated = new RestTemplate()
        .exchange(
          baseUrl,
          HttpMethod.PUT,
          new HttpEntity<>(animeToBeUpdated, createJsonHeader()),
          Void.class
        );

    log.info(samuraiChamplooUpdated);

    ResponseEntity<Void> samuraiChamplooDeleted = new RestTemplate().exchange(baseUrl + "{id}",
        HttpMethod.DELETE,
        null,
        Void.class,
        animeToBeUpdated != null ? animeToBeUpdated.getId() : null);

    log.info(samuraiChamplooDeleted);
  }

  private static HttpHeaders createJsonHeader() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    return httpHeaders;
  }
}
