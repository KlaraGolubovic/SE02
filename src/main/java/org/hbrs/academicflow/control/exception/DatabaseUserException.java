package org.hbrs.academicflow.control.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DatabaseUserException extends Exception {

  private final String reason;

  public DatabaseUserException() {
    this("An error occured while executing a query");
  }
}
