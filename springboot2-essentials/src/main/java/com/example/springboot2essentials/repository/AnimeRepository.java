package com.example.springboot2essentials.repository;

import com.example.springboot2essentials.domain.Anime;

import java.util.List;

public interface AnimeRepository {
  List<Anime> listAll();
}
