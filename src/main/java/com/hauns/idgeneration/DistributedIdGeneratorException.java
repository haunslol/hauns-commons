package com.hauns.idgeneration;

public final class DistributedIdGeneratorException extends RuntimeException {

  private static final long serialVersionUID = 5861310537366287163L;

  public DistributedIdGeneratorException() {
    super();
  }

  public DistributedIdGeneratorException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public DistributedIdGeneratorException(final String message) {
    super(message);
  }

  public DistributedIdGeneratorException(final Throwable cause) {
    super(cause);
  }
}
