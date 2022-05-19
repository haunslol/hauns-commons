package com.hauns.errorhandling;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class RestException extends ResponseStatusException {
  private final transient Object[] args;

  public RestException(HttpStatus status) {
    super(status);
    this.args = null;
  }

  public RestException(HttpStatus status, @Nullable String message) {
    super(status, message);
    this.args = null;
  }

  public RestException(HttpStatus status, @Nullable String messageKey, @Nullable Throwable cause) {
    super(status, messageKey, cause);
    this.args = null;
  }

  public RestException(int rawStatusCode, @Nullable String messageKey, @Nullable Throwable cause) {
    super(rawStatusCode, messageKey, cause);
    this.args = null;
  }

  public RestException(HttpStatus status, @Nullable String messageKey, @Nullable Object[] args) {
    super(status, messageKey);
    this.args = args;
  }

  public RestException(
      HttpStatus status,
      @Nullable String messageKey,
      @Nullable Throwable cause,
      @Nullable Object[] args) {
    super(status, messageKey, cause);
    this.args = args;
  }

  public Object[] getArgs() {
    return args;
  }

  public String getMessageKey() {
    return getReason();
  }
}
