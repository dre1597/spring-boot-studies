package com.example.springboot2essentials.handler;

import com.example.springboot2essentials.exception.BadRequestException;
import com.example.springboot2essentials.exception.BadRequestExceptionDetails;
import com.example.springboot2essentials.exception.ExceptionDatails;
import com.example.springboot2essentials.exception.ValidationExceptionDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestDetails(BadRequestException badRequestException) {
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

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException methodArgumentNotValidException,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request
  ) {

    List<FieldError> fieldErrors = methodArgumentNotValidException.getFieldErrors();

    String fields = fieldErrors
        .stream()
        .map(FieldError::getField)
        .collect(Collectors.joining(","));

    String fieldsMessage = fieldErrors
        .stream()
        .map(FieldError::getDefaultMessage)
        .collect(Collectors.joining(","));

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

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex,
      @Nullable Object body,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request
  ) {

    ExceptionDatails exceptionDetails = ExceptionDatails.builder()
        .timestamp(LocalDateTime.now())
        .status(status.value())
        .title(ex.getCause().getMessage())
        .details(ex.getMessage())
        .developerMessage(ex.getClass().getName())
        .build();

    return new ResponseEntity<>(exceptionDetails, headers, status);
  }
}
