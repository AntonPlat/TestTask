package com.myproject.presentation.exceptions;

import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionsHandler extends ResponseEntityExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionsHandler.class);


  @ExceptionHandler(TaskNotFoundException.class)
  public final ResponseEntity handleTaskNotFoundException(TaskNotFoundException e) {
    logger.error(e.getMessage());
    return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public final ResponseEntity handleConstraintViolationException(ConstraintViolationException e) {
    logger.error(e.getMessage());
    return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
  }


}
