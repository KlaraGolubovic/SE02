package org.hbrs.academicflow.control.exception;

public class DatabaseUserException extends Exception {
  private String reason = null;

  public DatabaseUserException(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }
}
