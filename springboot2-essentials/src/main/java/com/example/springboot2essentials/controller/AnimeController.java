package com.example.springboot2essentials.controller;

import com.example.springboot2essentials.domain.Anime;
import com.example.springboot2essentials.service.AnimeService;
import com.example.springboot2essentials.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("animes")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {
  private final DateUtil dateUtil;

  private final AnimeService animeService;

  @GetMapping
  public ResponseEntity<List<Anime>> list() {
    log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
    return ResponseEntity.ok(animeService.listAll());
  }
}
