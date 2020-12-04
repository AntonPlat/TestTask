package com.myproject.presentation.exceptions;

public class TaskNotFoundException extends RuntimeException {

  public TaskNotFoundException(String format) {
    super(format);
  }
}
