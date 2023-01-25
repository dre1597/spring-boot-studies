package com.example.springboot2essentials.mapper;

import com.example.springboot2essentials.domain.Anime;
import com.example.springboot2essentials.request.AnimePostRequestBody;
import com.example.springboot2essentials.request.AnimePutRequestBody;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AnimeMapper {
  AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Anime toAnime(AnimePostRequestBody animePostRequestBody);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Anime toAnime(AnimePutRequestBody animePutRequestBody);
}
