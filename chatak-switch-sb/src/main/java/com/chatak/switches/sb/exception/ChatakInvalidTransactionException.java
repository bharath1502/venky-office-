/**
 * 
 */
package com.chatak.switches.sb.exception;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 24-Jan-2015 2:41:58 PM
 * @version 1.0
 */
public class ChatakInvalidTransactionException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = -4884063236935716066L;

  /**
   * 
   */
  public ChatakInvalidTransactionException() {
    super("Invalid Transaction Id");
  }

  /**
   * @param message
   */
  public ChatakInvalidTransactionException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public ChatakInvalidTransactionException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message
   * @param cause
   */
  public ChatakInvalidTransactionException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @param message
   * @param cause
   * @param enableSuppression
   * @param writableStackTrace
   */
  public ChatakInvalidTransactionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
