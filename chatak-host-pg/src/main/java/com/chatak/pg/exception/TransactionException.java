package com.chatak.pg.exception;

/**
 * The exception class handle and catch the transaction common
 * errors
 */
public class TransactionException extends Exception {
  private static final long serialVersionUID = -907558219319972542L;

  /**
   * Default constructor for TransactionException
   */
  public TransactionException() {
    super();
  }

  /**
   * Partial constructor
   * 
   * @param message
   */
  public TransactionException(String message) {
    super(message);
  }

  /**
   * Partial constructor
   * 
   * @param cause
   */
  public TransactionException(Throwable cause) {
    super(cause);
  }

  /**
   * Full constructor
   * 
   * @param message
   * @param cause
   */
  public TransactionException(String message, Throwable cause) {
    super(message, cause);
  }
}