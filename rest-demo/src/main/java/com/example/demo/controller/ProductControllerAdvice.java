package com.example.demo.controller;

import com.example.demo.exception.InvalidFieldsException;
import com.example.demo.exception.InvalidPriceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ProductControllerAdvice extends ResponseEntityExceptionHandler {

  private static final String MESSAGE_FIELD = "Message";

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> captureInternalServerError() {
    Map<String, Object> body = new HashMap<>();

    body.put(MESSAGE_FIELD, "Internal Server Error");

    return ResponseEntity.internalServerError().body(body);
  }

  @ExceptionHandler(InvalidFieldsException.class)
  public ResponseEntity<Object> captureInvalidFieldsException() {
    Map<String, Object> body = new HashMap<>();

    body.put(MESSAGE_FIELD, "Invalid fields");

    return ResponseEntity.unprocessableEntity().body(body);
  }

  @ExceptionHandler(InvalidPriceException.class)
  public ResponseEntity<Object> captureInvalidPriceException() {
    Map<String, Object> body = new HashMap<>();

    body.put(MESSAGE_FIELD, "Price need to be positive");

    return ResponseEntity.unprocessableEntity().body(body);
  }
}
