package org.hbrs.appname.control.exception;

public class DatabaseUserException extends Exception {
    private String reason = null;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public DatabaseUserException(String reason) {
        this.reason = reason;
    }
}
