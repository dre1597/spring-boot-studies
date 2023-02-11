package com.example.springboot2essentials.controller;

import com.example.springboot2essentials.domain.Anime;
import com.example.springboot2essentials.request.AnimePostRequestBody;
import com.example.springboot2essentials.request.AnimePutRequestBody;
import com.example.springboot2essentials.service.AnimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
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
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successful operation"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @Operation(summary = "List all animes paginated and with the possibility of sorting", description = "The default size is 20, use the parameter size to change the default value.", tags = {"anime"})
  public ResponseEntity<Page<Anime>> find(@ParameterObject Pageable pageable) {
    return ResponseEntity.ok(animeService.find(pageable));
  }

  @GetMapping(path = "/all")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successful operation"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @Operation(summary = "List all animes without pagination and sorting", tags = {"anime"})
  public ResponseEntity<List<Anime>> findAll() {
    return ResponseEntity.ok(animeService.findAllNonPageable());
  }

  @GetMapping(path = "/{id}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successful operation"),
      @ApiResponse(responseCode = "400", description = "Anime does not exist in the database"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @Operation(summary = "Find an anime by thier id", tags = {"anime"})
  public ResponseEntity<Anime> findById(@PathVariable long id) {
    return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
  }

  @GetMapping("/filterBy")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successful operation"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @Operation(summary = "Find an anime by thier name", tags = {"anime"})
//  public ResponseEntity<List<Anime>> findByName(@RequestParam(defaultValue = "") String name, @AuthenticationPrincipal UserDetails userDetals) {
  public ResponseEntity<List<Anime>> findByName(@RequestParam(defaultValue = "") String name) {
    return ResponseEntity.ok(animeService.findByName(name));
  }

  @PostMapping("/admin")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successful operation"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @Operation(summary = "Create a new anime", tags = {"anime"})
  public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody) {
    return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED);
  }

  @PutMapping("/admin")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Successful operation"),
      @ApiResponse(responseCode = "400", description = "Anime does not exist in the database"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @Operation(summary = "Update an anime", tags = {"anime"})
  public ResponseEntity<Void> update(@RequestBody @Valid AnimePutRequestBody animePutRequestBody) {
    animeService.replace(animePutRequestBody);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping(path = "/admin/{id}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Successful Operation"),
      @ApiResponse(responseCode = "400", description = "Anime does not exist in the database"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @Operation(summary = "Delete an anime by their id", tags = {"anime"})
  public ResponseEntity<Void> delete(@PathVariable long id) {
    animeService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
