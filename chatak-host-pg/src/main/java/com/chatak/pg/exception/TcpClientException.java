package com.chatak.pg.exception;


public class TcpClientException extends Exception {

  private static final long serialVersionUID = -8143618939733276635L;

  /**
   * Default constructor
   */
  public TcpClientException() {
    super();
  }

  /**
   * Partial constructor
   * 
   * @param message
   */
  public TcpClientException(String message) {
    super(message);
  }

  /**
   * Partial constructor
   * 
   * @param cause
   */
  public TcpClientException(Throwable cause) {
    super(cause);
  }

  /**
   * Full constructor
   * 
   * @param message
   * @param cause
   */
  public TcpClientException(String message, Throwable cause) {
    super(message, cause);
  }
}
