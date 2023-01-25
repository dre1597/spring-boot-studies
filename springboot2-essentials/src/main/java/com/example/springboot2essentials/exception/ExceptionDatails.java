package com.example.springboot2essentials.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDatails {
  protected String title;
  protected int status;
  protected String details;
  protected String developerMessage;
  protected LocalDateTime timestamp;
}
