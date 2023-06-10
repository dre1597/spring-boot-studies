package com.example.mbspringboot3.product.exception;

public class NotFoundException extends RuntimeException {
  public NotFoundException() {
    super("Product not found");
  }
}
