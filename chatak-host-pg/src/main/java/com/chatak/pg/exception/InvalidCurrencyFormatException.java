package com.chatak.pg.exception;


public class InvalidCurrencyFormatException extends Exception {

  private static final long serialVersionUID = -5714429062019360702L;

  /**
   * Default constructor
   */
  public InvalidCurrencyFormatException() {
    super();
  }

  /**
   * Partial constructor
   * 
   * @param message
   */
  public InvalidCurrencyFormatException(String message) {
    super(message);
  }

  /**
   * Partial constructor
   * 
   * @param message
   * @param cause
   */
  public InvalidCurrencyFormatException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Full constructor
   * 
   * @param cause
   */
  public InvalidCurrencyFormatException(Throwable cause) {
    super(cause);
  }
}