package com.example.springboot2essentials.controller;

import com.example.springboot2essentials.domain.Anime;
import com.example.springboot2essentials.request.AnimePostRequestBody;
import com.example.springboot2essentials.request.AnimePutRequestBody;
import com.example.springboot2essentials.service.AnimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("animes")
@RequiredArgsConstructor
public class AnimeController {
  private final AnimeService animeService;

  @GetMapping
  public ResponseEntity<Page<Anime>> find(Pageable pageable) {
    return ResponseEntity.ok(animeService.find(pageable));
  }

  @GetMapping(path = "/all")
  public ResponseEntity<List<Anime>> findAll() {
    return ResponseEntity.ok(animeService.findAllNonPageable());
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<Anime> findById(@PathVariable long id) {
    return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
  }

  @GetMapping("/filterBy")
  public ResponseEntity<List<Anime>> findByName(@RequestParam(defaultValue = "") String name) {
    return ResponseEntity.ok(animeService.findByName(name));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Anime save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody) {
    return animeService.save(animePostRequestBody);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> update(@RequestBody AnimePutRequestBody animePutRequestBody) {
    animeService.replace(animePutRequestBody);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    animeService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
