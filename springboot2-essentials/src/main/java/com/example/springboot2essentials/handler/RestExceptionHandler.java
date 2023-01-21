package com.example.springboot2essentials.handler;

import com.example.springboot2essentials.exception.BadRequestException;
import com.example.springboot2essentials.exception.BadRequestExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestExceptionDetails(BadRequestException badRequestException) {
    return new ResponseEntity<>(
        BadRequestExceptionDetails
            .builder()
            .title("Bad Request Exception")
            .status(HttpStatus.BAD_REQUEST.value())
            .details(badRequestException.getMessage())
            .developerMessage(badRequestException.getClass().getName())
            .timestamp(LocalDateTime.now())
            .build(), HttpStatus.BAD_REQUEST
    );
  }
}
