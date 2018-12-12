package com.chatak.pg.exception;


public class ServiceException extends Throwable {

  private static final long serialVersionUID = -7457831330030646865L;

  /**
   * Default constructor
   */
  public ServiceException() {
    super();
  }

  /**
   * Partial constructor
   * 
   * @param message
   */
  public ServiceException(String message) {
    super(message);
  }

  /**
   * Partial constructor
   * 
   * @param cause
   */
  public ServiceException(Throwable cause) {
    super(cause);
  }

  /**
   * Full constructor
   * 
   * @param message
   * @param cause
   */
  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}