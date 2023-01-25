package com.example.springboot2essentials.handler;

import com.example.springboot2essentials.exception.BadRequestException;
import com.example.springboot2essentials.exception.BadRequestExceptionDetails;
import com.example.springboot2essentials.exception.ValidationExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationExceptionDetails> handlerMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {

    List<FieldError> fieldErrors = methodArgumentNotValidException.getFieldErrors();

    String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
    String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
    
    return new ResponseEntity<>(
        ValidationExceptionDetails
            .builder()
            .title("Invalid Fields Exception")
            .status(HttpStatus.BAD_REQUEST.value())
            .details("Check the field(s) error")
            .developerMessage(methodArgumentNotValidException.getClass().getName())
            .timestamp(LocalDateTime.now())
            .fields(fields)
            .fieldsMessage(fieldsMessage)
            .build(), HttpStatus.BAD_REQUEST
    );
  }
}
