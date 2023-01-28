package com.example.springboot2essentials.service;

import com.example.springboot2essentials.domain.Anime;
import com.example.springboot2essentials.exception.BadRequestException;
import com.example.springboot2essentials.mapper.AnimeMapper;
import com.example.springboot2essentials.repository.AnimeRepository;
import com.example.springboot2essentials.request.AnimePostRequestBody;
import com.example.springboot2essentials.request.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

  private final AnimeRepository animeRepository;

  public Page<Anime> find(Pageable pageable) {
    return animeRepository.findAll(pageable);
  }

  public List<Anime> findAllNonPageable() {
    return animeRepository.findAll();
  }

  public Anime findByIdOrThrowBadRequestException(long id) {
    return animeRepository.findById(id)
        .orElseThrow(() -> new BadRequestException("Anime not found"));
  }

  public List<Anime> findByName(String name) {
    return animeRepository.findByName(name);
  }

  public Anime save(AnimePostRequestBody animePostRequestBody) {
    return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
  }

  public void replace(AnimePutRequestBody animePutRequestBody) {
    Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());

    Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
    anime.setId(savedAnime.getId());

    animeRepository.save(anime);
  }

  public void delete(long id) {
    animeRepository.delete(findByIdOrThrowBadRequestException(id));
  }
}


