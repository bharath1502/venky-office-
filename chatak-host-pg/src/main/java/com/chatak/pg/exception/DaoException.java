package com.chatak.pg.exception;


public class DaoException extends Throwable {

  private static final long serialVersionUID = -7457831330030646865L;

  /**
   * Default constructor
   */
  public DaoException() {
    super();
  }

  /**
   * Partial constructor
   * 
   * @param message
   */
  public DaoException(String message) {
    super(message);
  }

  /**
   * Partial constructor
   * 
   * @param cause
   */
  public DaoException(Throwable cause) {
    super(cause);
  }

  /**
   * Full constructor
   * 
   * @param message
   * @param cause
   */
  public DaoException(String message, Throwable cause) {
    super(message, cause);
  }
}