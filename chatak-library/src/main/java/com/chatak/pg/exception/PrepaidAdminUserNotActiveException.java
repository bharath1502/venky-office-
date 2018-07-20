package com.chatak.pg.exception;

public class PrepaidAdminUserNotActiveException extends Exception {
  private static final long serialVersionUID = 1L;

  private String message;

  private String errorCode;

  public PrepaidAdminUserNotActiveException() {
    setErrorCode("1100");
    setMessage("Admin user is not in active status. Please contact Prepaid support team for more information");
  }

  public PrepaidAdminUserNotActiveException(String errorCode, String errorMessage) {
    setErrorCode(errorCode);
    setMessage(errorMessage);
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }
}
