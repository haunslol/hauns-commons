package com.hauns.errorhandling;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

  private final MessageSource messageSource;

  @Autowired
  public RestExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(RestException.class)
  public ResponseEntity<RestMessage> handleRestException(
      @NotNull RestException exception, Locale locale) {
    String errorMessage = exception.getMessageKey();
    try {
      errorMessage =
          messageSource.getMessage(exception.getMessageKey(), exception.getArgs(), locale);
    } catch (NoSuchMessageException e) {
      log.error("Missing message key: " + exception.getMessageKey(), e);
    }
    return new ResponseEntity<>(new RestMessage(errorMessage), HttpStatus.BAD_REQUEST);
  }
}
