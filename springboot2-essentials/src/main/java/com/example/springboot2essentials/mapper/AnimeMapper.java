package com.example.springboot2essentials.mapper;

import com.example.springboot2essentials.domain.Anime;
import com.example.springboot2essentials.request.AnimePostRequestBody;
import com.example.springboot2essentials.request.AnimePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {
  public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

  public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);

  public abstract Anime toAnime(AnimePutRequestBody animePutRequestBody);
}
