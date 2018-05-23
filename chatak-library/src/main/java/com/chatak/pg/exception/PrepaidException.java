package com.chatak.pg.exception;

public class PrepaidException extends Exception {

  private static final long serialVersionUID = 642695133294263634L;

  private String message;

  private String reason;

  private String errorCode;

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

}
