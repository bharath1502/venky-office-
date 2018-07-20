package com.chatak.pg.exception;

public class HttpClientException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String httpErrorCode;

  private int statusCode;

  public HttpClientException(String httpErrorCode, int statusCode) {
    super();
    this.httpErrorCode = httpErrorCode;
    this.statusCode = statusCode;
  }

  public String getHttpErrorCode() {
    return httpErrorCode;
  }

  public void setHttpErrorCode(String httpErrorCode) {
    this.httpErrorCode = httpErrorCode;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }
}
